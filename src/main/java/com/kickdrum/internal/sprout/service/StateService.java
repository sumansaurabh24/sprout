package com.kickdrum.internal.sprout.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kickdrum.internal.sprout.entity.State;

@Service
public interface StateService {

	State save(State state);

	List<State> findAll();

	State findStateBySchemaAndTable(String schema, String table);

	State findById(Integer id);

}
