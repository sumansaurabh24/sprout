package com.kickdrum.internal.sprout.builder;

import com.kickdrum.internal.sprout.entity.Operation;
import com.kickdrum.internal.sprout.entity.State;

import java.util.List;

public class StateBuilder {
    private Integer id;
    private Integer scriptId;
    private String schema;
    private String table;
    private String columns;
    private List<Operation> operations;

    public StateBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public StateBuilder setScriptId(Integer scriptId) {
        this.scriptId = scriptId;
        return this;
    }

    public StateBuilder setSchema(String schema) {
        this.schema = schema;
        return this;
    }

    public StateBuilder setTable(String table) {
        this.table = table;
        return this;
    }

    public StateBuilder setColumns(String columns) {
        this.columns = columns;
        return this;
    }

    public StateBuilder setOperations(List<Operation> operations) {
        this.operations = operations;
        return this;
    }

    public State createTableState() {
        return new State(id, scriptId, schema, table, columns, operations);
    }
}