package com.kickdrum.internal.sprout.model;

import java.util.List;

import com.kickdrum.internal.sprout.entity.Operation;
import com.kickdrum.internal.sprout.entity.State;

public class StateOperationWrapper {

    private State state;
    private List<Operation> operations;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}
