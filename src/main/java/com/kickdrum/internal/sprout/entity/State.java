package com.kickdrum.internal.sprout.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.kickdrum.internal.sprout.enums.StateOperation;

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
    
    @Column
    private Integer scriptId;

    @Transient
    private StateOperation operation;

    @Transient
    private List<String> columnList;

    public State() {
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

    public List<String> getColumnList() {
        List<String> list = new ArrayList<>(Arrays.asList(columns.split(",")));
        return list;
    }

    public void setColumnList(List<String> columnList) {
        this.columnList = columnList;
    }

	/**
	 * @return the scriptId
	 */
	public Integer getScriptId() {
		return scriptId;
	}

	/**
	 * @param scriptId the scriptId to set
	 */
	public void setScriptId(Integer scriptId) {
		this.scriptId = scriptId;
	}
    
    
}