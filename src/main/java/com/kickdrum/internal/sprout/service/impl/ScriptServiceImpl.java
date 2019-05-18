package com.kickdrum.internal.sprout.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import com.kickdrum.internal.sprout.enums.StateOperation;
import com.kickdrum.internal.sprout.util.AppUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kickdrum.internal.sprout.dao.ScriptDao;
import com.kickdrum.internal.sprout.entity.Operation;
import com.kickdrum.internal.sprout.entity.Script;
import com.kickdrum.internal.sprout.entity.State;
import com.kickdrum.internal.sprout.model.StateOperationWrapper;
import com.kickdrum.internal.sprout.service.OperationService;
import com.kickdrum.internal.sprout.service.ScriptService;
import com.kickdrum.internal.sprout.service.StateService;
import com.kickdrum.internal.sprout.util.SqlParser;

import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.statement.Statement;

@Component
@Slf4j
public class ScriptServiceImpl implements ScriptService {

	@Autowired
	private ScriptDao scriptDao;

	@Autowired
	private SqlParser sqlParser;

	@Autowired
	private StateService stateService;

	@Autowired
	private OperationService operationService;

	@Override
	public Script findById(Long id) {
		return scriptDao.findById(id).orElse(null);
	}

	@Override
	public Script save(Script scriptInput) {
		return scriptDao.save(scriptInput);
	}

	@Override
	public List<Script> findAll() {
		return scriptDao.findAll();
	}

	@Override
	public void process(Script script) throws JSQLParserException {
		// validate the script
		List<Statement> statements = sqlParser.validateScript(script.getScriptData());

		// persist the script
		save(script);

		// persist state and operation
		saveStateAndOperation(script, statements);

	}

	private void saveStateAndOperation(Script script, List<Statement> statements) {
	    String schema = "test";
	    Integer userId = 2;

		// set database.schema name
		sqlParser.setSchema(schema);
		// set user id as modifier
		sqlParser.setModifier(userId);

		List<StateOperationWrapper> stateOperationWrappers = sqlParser.parse(statements);
		for (StateOperationWrapper wrapper : stateOperationWrappers) {
		    State state = wrapper.getState();
		    //check the operation
            if(!state.getOperation().equals(StateOperation.ADD)) {
				State existingState = stateService.findStateBySchemaAndTable(state.getSchema(), state.getTable());
				wrapper = AppUtil.modifyStateColumnList(wrapper, existingState);
				state = wrapper.getState();
            }
			state = stateService.save(state);
			saveOperations(wrapper.getOperations(), state.getId(), script.getId());
		}
	}



    private void saveOperations(List<Operation> operations, Integer stateId, Integer scriptId){
        for (Operation operation : operations) {
            operation.setStateId(stateId);
            operation.setScriptId(scriptId);
            operationService.save(operation);
        }
    }
}
