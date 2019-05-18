package com.kickdrum.internal.sprout.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kickdrum.internal.sprout.dao.StateDao;
import com.kickdrum.internal.sprout.entity.State;
import com.kickdrum.internal.sprout.service.StateService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StateServiceImpl implements StateService {

	@Autowired
	private StateDao stateDao;

	@Override
	public State save(State state) {
		return stateDao.save(state);
	}

	@Override
	public List<State> getAllStates() {
		return stateDao.findLatestState();
	}

	@Override
	public State findStateBySchemaAndTable(String schema, String table) {
		return stateDao.findBySchemaAndTable(schema, table);
	}
}
