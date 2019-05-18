package com.kickdrum.internal.sprout.service.impl;

import com.kickdrum.internal.sprout.dao.ScriptDao;
import com.kickdrum.internal.sprout.entity.Operation;
import com.kickdrum.internal.sprout.entity.Script;
import com.kickdrum.internal.sprout.entity.State;
import com.kickdrum.internal.sprout.enums.StateOperation;
import com.kickdrum.internal.sprout.exception.SproutException;
import com.kickdrum.internal.sprout.model.StateOperationWrapper;
import com.kickdrum.internal.sprout.service.OperationService;
import com.kickdrum.internal.sprout.service.ScriptService;
import com.kickdrum.internal.sprout.service.StateService;
import com.kickdrum.internal.sprout.util.AppUtil;
import com.kickdrum.internal.sprout.util.SqlParser;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.alter.AlterExpression;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.table.ForeignKeyIndex;
import net.sf.jsqlparser.statement.create.table.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ScriptServiceImpl implements ScriptService {

	@Autowired
	private ScriptDao scriptDao;

	@Autowired
	private SqlParser sqlParser;

	@Autowired
	private StateService stateService;

	@Autowired
	private OperationService operationService;

	@Override
	public Script findById(Long id) {
		return scriptDao.findById(id).orElse(null);
	}

	@Override
	public Script save(Script scriptInput) {
		return scriptDao.save(scriptInput);
	}

	@Override
	public List<Script> findAll() {
		return scriptDao.findAll();
	}

	@Override
	public void process(Script script) throws JSQLParserException, SproutException {
		// validate the script
		List<Statement> statements = sqlParser.validateScript(script.getScriptData());

		findDependencies(statements, stateService.getAllStates());
		// persist the script
		save(script);

		// persist state and operation
		saveStateAndOperation(script, statements);

	}

	private void saveStateAndOperation(Script script, List<Statement> statements) {
	    String schema = "test";
	    Integer userId = 2;

		// set database.schema name
		sqlParser.setSchema(schema);
		// set user id as modifier
		sqlParser.setModifier(userId);

		List<StateOperationWrapper> stateOperationWrappers = sqlParser.parse(statements);
		for (StateOperationWrapper wrapper : stateOperationWrappers) {
		    State state = wrapper.getState();
		    //check the operation
            if(!state.getOperation().equals(StateOperation.ADD)) {
				State existingState = stateService.findStateBySchemaAndTable(state.getSchema(), state.getTable());
				wrapper = AppUtil.modifyStateColumnList(wrapper, existingState);
				state = wrapper.getState();
            }
			state = stateService.save(state);
			saveOperations(wrapper.getOperations(), state.getId(), script.getId());
		}
	}

    private void saveOperations(List<Operation> operations, Integer stateId, Integer scriptId){
        for (Operation operation : operations) {
            operation.setStateId(stateId);
            operation.setScriptId(scriptId);
            operationService.save(operation);
        }
    }

	private void findDependencies(List<Statement> statements, List<State> allStates) throws SproutException {
		for (Statement statement : statements) {
			if (statement instanceof CreateTable) {
				String tableName = ((CreateTable) statement).getTable().getName();
				checkIfTableAlreadyExists(allStates, tableName);
				List<Index> indexes = ((CreateTable) (statement)).getIndexes();
				if (indexes != null) {
					checkForAnyKeyDependency(allStates, indexes, tableName);
				}
			} else if (statement instanceof Alter) {
				List<AlterExpression> expressions = ((Alter) statement).getAlterExpressions();
				String tableName = ((Alter) statement).getTable().getName();
				checkIfTableExists(allStates, tableName);
				for (AlterExpression alterExpression : expressions) {
					String operation = alterExpression.getOperation().name();
					switch (operation) {
					case "ADD":
						checkIfColumnNotExists(allStates, alterExpression, tableName);
						break;
					case "DROP":
						checkIfColumnExistsAlready(allStates, alterExpression, tableName);
						break;
					default:
						checkIfColumnExistsAlready(allStates, alterExpression, tableName);
					}
				}

			}
		}
	}

	private void checkIfTableExists(List<State> allStates, String tableName) throws SproutException {
		if (allStates.stream().filter(state -> state.getTable().equalsIgnoreCase(tableName))
				.collect(Collectors.toList()).size() == 0) {
			throw new SproutException("Table " + tableName + " does not exist");
		}
	}

	private void checkIfColumnExistsAlready(List<State> allStates, AlterExpression alterExpression, String tableName)
			throws SproutException {

		if (allStates.stream()
				.filter(state -> state.getTable().equalsIgnoreCase(tableName)
						&& state.getColumns().contains(alterExpression.getColDataTypeList().get(0).getColumnName()))
				.collect(Collectors.toList()).size() == 0) {
			throw new SproutException("Column " + tableName + "."
					+ alterExpression.getColDataTypeList().get(0).getColumnName() + " doesn't exist already");
		}
	}

	private void checkIfColumnNotExists(List<State> allStates, AlterExpression alterExpression, String tableName)
			throws SproutException {
		if (allStates.stream()
				.filter(state -> state.getTable().equalsIgnoreCase(tableName)
						&& state.getColumns().contains(alterExpression.getColDataTypeList().get(0).getColumnName()))
				.collect(Collectors.toList()).size() > 0) {
			throw new SproutException("Column " + tableName + "."
					+ alterExpression.getColDataTypeList().get(0).getColumnName() + " exists already");
		}
	}

	private void checkForAnyKeyDependency(List<State> allStates, List<Index> indexes, String tableName)
			throws SproutException {
		for (Index index : indexes) {
			if (index instanceof ForeignKeyIndex) {
				ForeignKeyIndex fki = (ForeignKeyIndex) index;
				String columnName = fki.getColumnsNames().get(0);
				String referencesTable = fki.getTable().getName();
				String referencedColumn = fki.getReferencedColumnNames().get(0);
				if (allStates.stream()
						.filter(state -> state.getTable().equalsIgnoreCase(referencesTable)
								&& state.getColumns().contains(referencedColumn))
						.collect(Collectors.toList()).size() == 0) {
					throw new SproutException(tableName + "." + columnName + " ForeignKey - " + referencesTable + "."
							+ referencedColumn + " does not exists");
				}

			}

		}
	}

	private void checkIfTableAlreadyExists(List<State> allStates, String tableName) throws SproutException {
		if (allStates.stream().filter(state -> state.getTable().equalsIgnoreCase(tableName))
				.collect(Collectors.toList()).size() > 0) {
			throw new SproutException(tableName + " already exists");
		}
	}
}
