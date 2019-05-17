package com.kickdrum.internal.sprout.builder;

import java.time.Instant;

import com.kickdrum.internal.sprout.entity.Operation;

public class OperationBuilder {
    private Integer stateId;
    private String operation;
    private String affectedColumns;
    private Integer modifiedBy;
    private Instant modifiedAt;

    public OperationBuilder setStateId(Integer stateId) {
        this.stateId = stateId;
        return this;
    }

    public OperationBuilder setOperation(String operation) {
        this.operation = operation;
        return this;
    }

    public OperationBuilder setAffectedColumns(String affectedColumns) {
        this.affectedColumns = affectedColumns;
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

    public Operation createTableOperation() {
        return new Operation(stateId, operation, affectedColumns, modifiedBy, modifiedAt);
    }
}