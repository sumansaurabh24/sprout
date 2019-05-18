package com.kickdrum.internal.sprout.util;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.alter.AlterExpression;
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
     * Parse batch query statements
     *
     * @param statements
     * @return List<StateOperationWrapper>
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
     * @return List<Statement>
     * @throws JSQLParserException
     */
    public List<Statement> validateScript(String query) throws JSQLParserException {
        String[] queryList = query.split(";");
        List<String> queries = Arrays.asList(queryList);
        List<Statement> statements = new ArrayList<>();
        for (String queryStr : queries) {
            Statement statement = CCJSqlParserUtil.parse(queryStr);
            statements.add(statement);
        }
        return statements;
    }

    /**
     * Get {@link State} Object from string query
     *
     * @param statement
     * @return
     */
    public StateOperationWrapper getState(Statement statement) {

        if (statement instanceof CreateTable) {
            return getStateForCreateTable((CreateTable) statement);
        }else if(statement instanceof Alter){
            return getStateFromAlter((Alter) statement);
        }

        return null;
    }

    /**
     * Get State for CREATE TABLE query
     *
     * @param createTable
     * @return StateOperationWrapper
     */
    private StateOperationWrapper getStateForCreateTable(CreateTable createTable) {
        List<ColumnDefinition> columnDefinitions = createTable.getColumnDefinitions();
        Table table = createTable.getTable();
        List<Operation> operations = new ArrayList<>();

        List<String> columns = new ArrayList<>();
        for (ColumnDefinition columnDefinition : columnDefinitions) {
            columns.add(columnDefinition.getColumnName());
            Operation operation = new OperationBuilder().setOperation(StateOperation.ADD.toString())
                    .setAffectedColumn(columnDefinition.getColumnName()).setModifiedAt(Instant.now())
                    .setModifiedBy(modifier).createOperation();
            operations.add(operation);
        }
        String columnWithComma = StringUtils.join(columns, ",");

        State state = new StateBuilder().setColumns(columnWithComma).setSchema(schema).setTable(table.getName())
                .setOperation(StateOperation.ADD).createState();

        StateOperationWrapper stateOperationWrapper = new StateOperationWrapper();
        stateOperationWrapper.setState(state);
        stateOperationWrapper.setOperations(operations);
        return stateOperationWrapper;
    }

    /**
     * Get State from ALTER TABLE query
     *
     * @param alter
     * @return StateOperationWrapper
     */
    private StateOperationWrapper getStateFromAlter(Alter alter) {
        String table = alter.getTable().getName();
        List<AlterExpression> alterExpressions = alter.getAlterExpressions();
        List<Operation> operations = new ArrayList<>();

        //construct state
        State state = new StateBuilder()
                .setSchema(schema)
                .setTable(table)
                .setOperation(StateOperation.DROP)
                .createState();


        for (AlterExpression expression : alterExpressions) {
            String op = expression.getOperation().name();
            String column = null;

            // get affected column name
            if (StateOperation.ADD.name().equalsIgnoreCase(op)) {
                List<AlterExpression.ColumnDataType> colDataTypeList = expression.getColDataTypeList();
                for (AlterExpression.ColumnDataType columnDataType : colDataTypeList) {
                    column = columnDataType.getColumnName();
                }
            } else {
                column = expression.getColumnName();
            }

            //create operation object
            Operation operation = new OperationBuilder()
                    .setOperation(op)
                    .setModifiedAt(Instant.now())
                    .setAffectedColumn(column)
                    .setModifiedBy(modifier)
                    .createOperation();

            operations.add(operation);
        }

        StateOperationWrapper stateOperationWrapper = new StateOperationWrapper();
        stateOperationWrapper.setState(state);
        stateOperationWrapper.setOperations(operations);
        return stateOperationWrapper;
    }

}
