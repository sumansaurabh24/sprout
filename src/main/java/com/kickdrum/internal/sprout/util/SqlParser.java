package com.kickdrum.internal.sprout.util;

import com.kickdrum.internal.sprout.builder.OperationBuilder;
import com.kickdrum.internal.sprout.builder.StateBuilder;
import com.kickdrum.internal.sprout.entity.Operation;
import com.kickdrum.internal.sprout.entity.State;
import com.kickdrum.internal.sprout.enums.StateOperation;
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

    public List<State> parse(String query) {
        //split the batch file
        String[] queryList = query.split(";");
        List<String> queries = Arrays.asList(queryList);
        List<State> states = new ArrayList<>();
        for (String queryStr : queries) {
            states.add(getTableState(queryStr)) ;
        }
        return states;
    }

    private State getTableState(String query) {
        Statement statement = null;
        try {
            statement = CCJSqlParserUtil.parse(query);
        } catch (JSQLParserException e) {
            // TODO : throw proper exception
            return null;
        }

        if (statement instanceof CreateTable) {
            return getStateForCreateTable((CreateTable) statement);
        }

        return null;
    }

    private State getStateForCreateTable(CreateTable createTable) {
        List<ColumnDefinition> columnDefinitions = createTable.getColumnDefinitions();
        Table table = createTable.getTable();
        List<Operation> operations = new ArrayList<>();

        StringBuffer columns = new StringBuffer();
        for (ColumnDefinition columnDefinition : columnDefinitions) {
            columns.append(columnDefinition.getColumnName()).append(",");
            Operation operation = new OperationBuilder()
                    .setOperation(StateOperation.ADD.toString())
                    .setAffectedColumns(columnDefinition.getColumnName())
                    .setModifiedAt(Instant.now())
                    .setModifiedBy(modifier)
                    .createTableOperation();
            operations.add(operation);
        }

        State state = new StateBuilder()
                .setColumns(columns.toString())
                .setSchema(schema)
                .setTable(table.getName())
                .setOperations(operations)
                .createTableState();

        return state;
    }
}
