package com.kickdrum.internal.sprout.util;

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
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class SqlParser {

    private Integer modifier;
    private String schema;

    public void setModifier(Integer modifier) {
        this.modifier = modifier;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    /**
     * Parse batch query string
     * @param query
     * @return
     */
    public List<StateOperationWrapper> parse(String query) {
        //split the batch file
        String[] queryList = query.split(";");
        List<String> queries = Arrays.asList(queryList);
        List<StateOperationWrapper> states = new ArrayList<>();
        for (String queryStr : queries) {
            states.add(getState(queryStr));
        }
        return states;
    }

    /**
     * Get Statement Obejct
     *
     * @param query
     * @return
     */
    public Statement getStatement(String query){
        Statement statement = null;
        try {
            statement = CCJSqlParserUtil.parse(query);
        } catch (JSQLParserException e) {
            // TODO : throw proper exception
            statement = null;
        }
        return statement;
    }

    /**
     * Validate batch query string
     *
     * @param query
     * @return
     */
    public boolean validateScript(String query){
        boolean flag = true;
        String[] queryList = query.split(";");
        List<String> queries = Arrays.asList(queryList);
        for (String queryStr : queries) {
            Statement statement = getStatement(queryStr);
            if(statement == null){
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * Get {@link State} Object from string query
     *
     * @param query
     * @return
     */
    private StateOperationWrapper getState(String query) {
        Statement statement = getStatement(query);

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
            Operation operation = new OperationBuilder()
                    .setOperation(StateOperation.ADD.toString())
                    .setAffectedColumn(columnDefinition.getColumnName())
                    .setModifiedAt(Instant.now())
                    .setModifiedBy(modifier)
                    .createOperation();
            operations.add(operation);
        }

        State state = new StateBuilder()
                .setColumns(columns.toString())
                .setSchema(schema)
                .setTable(table.getName())
                .createState();

        StateOperationWrapper stateOperationWrapper = new StateOperationWrapper();
        stateOperationWrapper.setState(state);
        stateOperationWrapper.setOperations(operations);
        return stateOperationWrapper;
    }
}
