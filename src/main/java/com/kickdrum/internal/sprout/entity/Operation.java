package com.kickdrum.internal.sprout.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String operation;
    private String affectedColumn;
    private Integer modifiedBy;
    private Instant modifiedAt;
    private Integer stateId;

    public Operation() {
    }

    public Operation(Integer id, String operation, String affectedColumn, Integer modifiedBy, Instant modifiedAt, Integer stateId) {
        this.id = id;
        this.operation = operation;
        this.affectedColumn = affectedColumn;
        this.modifiedBy = modifiedBy;
        this.modifiedAt = modifiedAt;
        this.stateId = stateId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }
}
