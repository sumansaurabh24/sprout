package com.kickdrum.internal.sprout.entity;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer stateId;
    private Integer scriptId;
    private String operation;
    private String affectedColumn;
    private Integer modifiedBy;
    private Instant modifiedAt;

    public Operation() {
    }

    public Operation(Integer id, Integer stateId, Integer scriptId, String operation, String affectedColumn, Integer modifiedBy, Instant modifiedAt) {
        this.id = id;
        this.stateId = stateId;
        this.scriptId = scriptId;
        this.operation = operation;
        this.affectedColumn = affectedColumn;
        this.modifiedBy = modifiedBy;
        this.modifiedAt = modifiedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getScriptId() {
        return scriptId;
    }

    public void setScriptId(Integer scriptId) {
        this.scriptId = scriptId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getAffectedColumn() {
        return affectedColumn;
    }

    public void setAffectedColumn(String affectedColumn) {
        this.affectedColumn = affectedColumn;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
