package com.kickdrum.internal.sprout.util;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kickdrum.internal.sprout.builder.OperationBuilder;
import com.kickdrum.internal.sprout.builder.StateBuilder;
import com.kickdrum.internal.sprout.entity.Operation;
import com.kickdrum.internal.sprout.entity.State;
import com.kickdrum.internal.sprout.enums.StateOperation;
import com.kickdrum.internal.sprout.model.StateOperationWrapper;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;

@Component
public class SqlParser {

	private Integer modifier;
	private String schema;
	private static final Logger logger = LoggerFactory.getLogger(SqlParser.class);

	public void setModifier(Integer modifier) {
		this.modifier = modifier;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	/**
	 * Parse batch query string
	 * 
	 * @param statements
	 * @return
	 */
	public List<StateOperationWrapper> parse(List<Statement> statements) {
		// split the batch file
		List<StateOperationWrapper> states = new ArrayList<>();
		for (Statement statement : statements) {
			states.add(getState(statement));
		}
		return states;
	}

	/**
	 * Validate batch query string
	 *
	 * @param query
	 * @return
	 * @throws JSQLParserException
	 */
	public List<Statement> validateScript(String query) throws JSQLParserException {
		String[] queryList = query.split(";");
		List<String> queries = Arrays.asList(queryList);
		List<Statement> statements = new ArrayList<Statement>();
		for (String queryStr : queries) {
			Statement statement = CCJSqlParserUtil.parse(queryStr);
			statements.add(statement);
		}
		return statements;
	}

	/**
	 * Get {@link State} Object from string query
	 *
	 * @param query
	 * @return
	 */
	private StateOperationWrapper getState(Statement statement) {

		if (statement instanceof CreateTable) {
			return getStateForCreateTable((CreateTable) statement);
		}

		return null;
	}

	/**
	 * Get State for create table query
	 *
	 * @param createTable
	 * @return
	 */
	private StateOperationWrapper getStateForCreateTable(CreateTable createTable) {
		List<ColumnDefinition> columnDefinitions = createTable.getColumnDefinitions();
		Table table = createTable.getTable();
		List<Operation> operations = new ArrayList<>();

		StringBuffer columns = new StringBuffer();
		for (ColumnDefinition columnDefinition : columnDefinitions) {
			columns.append(columnDefinition.getColumnName()).append(",");
			Operation operation = new OperationBuilder().setOperation(StateOperation.ADD.toString())
					.setAffectedColumn(columnDefinition.getColumnName()).setModifiedAt(Instant.now())
					.setModifiedBy(modifier).createOperation();
			operations.add(operation);
		}

		State state = new StateBuilder().setColumns(columns.toString()).setSchema(schema).setTable(table.getName())
				.createState();

		StateOperationWrapper stateOperationWrapper = new StateOperationWrapper();
		stateOperationWrapper.setState(state);
		stateOperationWrapper.setOperations(operations);
		return stateOperationWrapper;
	}
}
