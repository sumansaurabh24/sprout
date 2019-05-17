package com.kickdrum.internal.sprout.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer scriptId;

    @Column(name = "schema_name")
    private String schema;

    @Column(name = "table_name")
    private String table;

    @Column(name = "column_list")
    private String columns;

    public State() {
    }

    public State(Integer scriptId, String schema, String table, String columns) {
        this.scriptId = scriptId;
        this.schema = schema;
        this.table = table;
        this.columns = columns;
    }

    public State(Integer id, Integer scriptId, String schema, String table, String columns
    ) {
        this.id = id;
        this.scriptId = scriptId;
        this.schema = schema;
        this.table = table;
        this.columns = columns;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScriptId() {
        return scriptId;
    }

    public void setScriptId(Integer scriptId) {
        this.scriptId = scriptId;
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
}
