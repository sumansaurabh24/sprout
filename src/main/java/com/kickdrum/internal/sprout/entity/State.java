package com.kickdrum.internal.sprout.entity;

import com.kickdrum.internal.sprout.enums.StateOperation;

import javax.persistence.*;

@Entity
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "schema_name")
    private String schema;

    @Column(name = "table_name")
    private String table;

    @Column(name = "column_list")
    private String columns;

    @Transient
    private StateOperation operation;

    public State() {
    }

    public State(String schema, String table, String columns) {
        this.schema = schema;
        this.table = table;
        this.columns = columns;
    }

    public State(Integer id, String schema, String table, String columns, StateOperation operation) {
        this.id = id;
        this.schema = schema;
        this.table = table;
        this.columns = columns;
        this.operation = operation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public StateOperation getOperation() {
        return operation;
    }

    public void setOperation(StateOperation operation) {
        this.operation = operation;
    }
}
