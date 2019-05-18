package com.kickdrum.internal.sprout.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.kickdrum.internal.sprout.entity.State;

@Service
public interface StateService {

	State save(State state);

	List<State> getAllStates();

	State findStateBySchemaAndTable(String schema, String table);

}
