/**
 * 
 */
package com.kickdrum.internal.sprout.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kickdrum.internal.sprout.builder.StateBuilder;
import com.kickdrum.internal.sprout.dao.ServerDao;
import com.kickdrum.internal.sprout.dao.StateDao;
import com.kickdrum.internal.sprout.entity.Server;
import com.kickdrum.internal.sprout.entity.State;
import com.kickdrum.internal.sprout.enums.StateOperation;
import com.kickdrum.internal.sprout.service.ServerService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author gowrish
 *
 */
@Component
@Slf4j
public class ServerServiceImpl implements ServerService {

	@Autowired
	ServerDao serverDao;
	@Autowired
	StateDao stateDao;

	@Override
	public List<Server> getConfigs() {
		return serverDao.findAll();
	}

	@Override
	public List<Map<String, Object>> getInitState() {
		List<State> initStates = new ArrayList<State>();
		List<Map<String, Object>> states = serverDao.fetchInitState();
		for (Map<String, Object> map : states) {
			String tableName = "";
			String columns = "";
			String tableSchema = "";
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if ("table_name".equals(entry.getKey())) {
					tableName = (String) entry.getValue();
				} else if ("columns".equals(entry.getKey())) {
					columns = (String) entry.getValue();
				} else if ("table_schema".equals(entry.getKey())) {
					tableSchema = (String) entry.getValue();
				}
			}
			State s = new StateBuilder()
					.setSchema(tableSchema)
					.setTable(tableName)
					.setColumns(columns)
					.setOperation(StateOperation.ADD)
					.createState();
			initStates.add(s);
		}
		stateDao.saveAll(initStates);
		return states;
	}

}
