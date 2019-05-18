package com.kickdrum.internal.sprout.builder;

import com.kickdrum.internal.sprout.entity.State;
import com.kickdrum.internal.sprout.enums.StateOperation;

public class StateBuilder {
    private Integer id;
    private String schema;
    private String table;
    private String columns;
    private StateOperation operation;

    public StateBuilder setId(Integer id) {
        this.id = id;
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

    public StateBuilder setOperation(StateOperation operation) {
        this.operation = operation;
        return this;
    }

    public State createState() {
        return new State(id, schema, table, columns, operation);
    }
}