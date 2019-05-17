package com.kickdrum.internal.sprout.builder;

import com.kickdrum.internal.sprout.entity.State;

public class StateBuilder {
    private Integer scriptId;
    private String schema;
    private String table;
    private String columns;
    private Integer id;

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

    public StateBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public State createState() {
        return new State(scriptId, schema, table, columns);
    }
}