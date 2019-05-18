package com.kickdrum.internal.sprout.builder;

import java.time.Instant;

import com.kickdrum.internal.sprout.entity.Operation;

public class OperationBuilder {
    private Integer id;
    private Integer stateId;
    private Integer scriptId;
    private String operation;
    private String affectedColumn;
    private Integer modifiedBy;
    private Instant modifiedAt;

    public OperationBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public OperationBuilder setStateId(Integer stateId) {
        this.stateId = stateId;
        return this;
    }

    public OperationBuilder setScriptId(Integer scriptId) {
        this.scriptId = scriptId;
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

    public Operation createOperation() {
        return new Operation(id, stateId, scriptId, operation, affectedColumn, modifiedBy, modifiedAt);
    }
}