package com.kickdrum.internal.sprout.entity;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String operation;
    private String affectedColumns;
    private Integer modifiedBy;
    private Instant modifiedAt;

    @ManyToOne()
    private State state;

    public Operation() {
    }

    public Operation(Integer tableStateId, String operation, String affectedColumns, Integer modifiedBy, Instant modifiedAt) {
//        this.tableStateId = tableStateId;
        this.operation = operation;
        this.affectedColumns = affectedColumns;
        this.modifiedBy = modifiedBy;
        this.modifiedAt = modifiedAt;
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

    public String getAffectedColumns() {
        return affectedColumns;
    }

    public void setAffectedColumns(String affectedColumns) {
        this.affectedColumns = affectedColumns;
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
