package com.kickdrum.internal.sprout.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class State implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	private Integer scriptId;
	@Column(name = "schema_name")
	private String schema;
	@Column(name = "table_name")
	private String table;
	@Column(name = "column_list")
	private String columns;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "table_state_id", referencedColumnName = "id")
	private List<Operation> operations;

	public State() {
	}

	public State(Integer scriptId, String schema, String table, String columns) {
		this.scriptId = scriptId;
		this.schema = schema;
		this.table = table;
		this.columns = columns;
	}

	public State(Integer id, Integer scriptId, String schema, String table, String columns,
			List<Operation> operations) {
		this.id = id;
		this.scriptId = scriptId;
		this.schema = schema;
		this.table = table;
		this.columns = columns;
		this.operations = operations;
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

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}
}
