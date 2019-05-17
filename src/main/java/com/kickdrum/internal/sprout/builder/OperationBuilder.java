package com.kickdrum.internal.sprout.builder;

import com.kickdrum.internal.sprout.entity.Operation;

import java.time.Instant;

public class OperationBuilder {
    private Integer id;
    private String operation;
    private String affectedColumn;
    private Integer modifiedBy;
    private Instant modifiedAt;
    private Integer stateId;

    public OperationBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public OperationBuilder setOperation(String operation) {
        this.operation = operation;
        return this;
    }

    public OperationBuilder setAffectedColumn(String affectedColumn) {
        this.affectedColumn = affectedColumn;
        return this;
    }

    public OperationBuilder setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public OperationBuilder setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public OperationBuilder setStateId(Integer stateId) {
        this.stateId = stateId;
        return this;
    }

    public Operation createOperation() {
        return new Operation(id, operation, affectedColumn, modifiedBy, modifiedAt, stateId);
    }
}