package com.kickdrum.internal.sprout.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kickdrum.internal.sprout.Node;
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
		Set<Integer> dependencies = findDependencies(statements, stateService.findAll());
		for (Integer integer : dependencies) {
			System.out.println("Dependent on " + integer);
		}
		String csd = StringUtils.join(dependencies, ",");
		script.setDependentScripts(csd);
		// persist the script
		save(script);
		// persist state and operation
		saveStateAndOperation(script, statements);

		listSequence();
	}

	private void listSequence() {
		Map<Integer, Node> nodesMap = new HashMap<Integer, Node>();
		Node zerothNode = new Node(0);
		List<Script> scripts = findAll();
		for (Script script : scripts) {
			Integer id = script.getId();
			Node sourceNode = nodesMap.get(id) == null ? new Node(id) : nodesMap.get(id);
			String dependentScripts = script.getDependentScripts();
			if (dependentScripts == null || dependentScripts.equals("")) {
				zerothNode.addneighbours(sourceNode);
			} else {
				for (String s : dependentScripts.split(",")) {
					Integer dId = Integer.parseInt(s);
					Node dependentNode = nodesMap.get(dId) == null ? new Node(dId) : nodesMap.get(dId);
					dependentNode.addneighbours(sourceNode);
				}
			}
		}

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
			// check the operation
			if (!state.getOperation().equals(StateOperation.ADD)) {
				State existingState = stateService.findStateBySchemaAndTable(state.getSchema(), state.getTable());
				wrapper = AppUtil.modifyStateColumnList(wrapper, existingState);
				state = wrapper.getState();
			}
			state.setScriptId(script.getId());
			state = stateService.save(state);
			saveOperations(wrapper.getOperations(), state.getId(), script.getId());
		}
	}

	private void saveOperations(List<Operation> operations, Integer stateId, Integer scriptId) {
		for (Operation operation : operations) {
			operation.setStateId(stateId);
			operation.setScriptId(scriptId);
			operationService.save(operation);
		}
	}

	private Set<Integer> findDependencies(List<Statement> statements, List<State> allStates) throws SproutException {
		Set<Integer> dependentScripts = new HashSet();
		for (Statement statement : statements) {
			if (statement instanceof CreateTable) {
				String tableName = ((CreateTable) statement).getTable().getName();
				checkIfTableAlreadyExists(allStates, tableName);
				List<Index> indexes = ((CreateTable) (statement)).getIndexes();
				if (indexes != null) {
					dependentScripts.addAll(checkForAnyKeyDependency(allStates, indexes, tableName));
				}
			} else if (statement instanceof Alter) {
				List<AlterExpression> expressions = ((Alter) statement).getAlterExpressions();
				String tableName = ((Alter) statement).getTable().getName();
				dependentScripts.add(checkIfTableExists(allStates, tableName));
				for (AlterExpression alterExpression : expressions) {
					String operation = alterExpression.getOperation().name();
					switch (operation) {
					case "ADD":
						checkIfColumnNotExists(allStates, alterExpression, tableName);
						break;
					case "DROP":
						dependentScripts.add(checkIfColumnExistsAlready(allStates, alterExpression, tableName));
						break;
					default:
						dependentScripts.add(checkIfColumnExistsAlready(allStates, alterExpression, tableName));
					}
				}

			}
			State state = sqlParser.getState(statement).getState();
			state.setColumns("");
			allStates.add(state);
		}
		return dependentScripts;
	}

	private Integer checkIfTableExists(List<State> allStates, String tableName) throws SproutException {
		List<State> filtered = allStates.stream().filter(state -> state.getTable().equalsIgnoreCase(tableName))
				.collect(Collectors.toList());
		if (filtered.size() == 0) {
			throw new SproutException("Table " + tableName + " does not exist");
		}
		return filtered.get(0).getScriptId();
	}

	private Integer checkIfColumnExistsAlready(List<State> allStates, AlterExpression alterExpression, String tableName)
			throws SproutException {
		List<State> filtered = allStates.stream()
				.filter(state -> state.getTable().equalsIgnoreCase(tableName)
						&& state.getColumns().contains(alterExpression.getColDataTypeList().get(0).getColumnName()))
				.collect(Collectors.toList());
		if (filtered.size() == 0) {
			throw new SproutException("Column " + tableName + "."
					+ alterExpression.getColDataTypeList().get(0).getColumnName() + " doesn't exist already");
		}
		return filtered.get(0).getScriptId();
	}

	private void checkIfColumnNotExists(List<State> allStates, AlterExpression alterExpression, String tableName)
			throws SproutException {
		List<State> filtered = allStates.stream()
				.filter(state -> state.getTable().equalsIgnoreCase(tableName)
						&& state.getColumns().contains(alterExpression.getColDataTypeList().get(0).getColumnName()))
				.collect(Collectors.toList());
		if (filtered.size() > 0) {
			throw new SproutException("Column " + tableName + "."
					+ alterExpression.getColDataTypeList().get(0).getColumnName() + " exists already");
		}
	}

	private Set<Integer> checkForAnyKeyDependency(List<State> allStates, List<Index> indexes, String tableName)
			throws SproutException {
		Set<Integer> dependencies = new HashSet<Integer>();
		for (Index index : indexes) {
			if (index instanceof ForeignKeyIndex) {
				ForeignKeyIndex fki = (ForeignKeyIndex) index;
				String columnName = fki.getColumnsNames().get(0);
				String referencesTable = fki.getTable().getName();
				String referencedColumn = fki.getReferencedColumnNames().get(0);
				List<State> filtered = allStates.stream()
						.filter(state -> state.getTable().equalsIgnoreCase(referencesTable)
								&& state.getColumns().contains(referencedColumn))
						.collect(Collectors.toList());
				if (filtered.size() == 0) {
					throw new SproutException(tableName + "." + columnName + " ForeignKey - " + referencesTable + "."
							+ referencedColumn + " does not exists");
				}
				if (filtered.get(0).getScriptId() != null) {
					dependencies.add(filtered.get(0).getScriptId());
				}

			}
		}
		return dependencies;
	}

	private void checkIfTableAlreadyExists(List<State> allStates, String tableName) throws SproutException {
		if (allStates.stream().filter(state -> state.getTable().equalsIgnoreCase(tableName))
				.collect(Collectors.toList()).size() > 0) {
			throw new SproutException(tableName + " already exists");
		}
	}
}