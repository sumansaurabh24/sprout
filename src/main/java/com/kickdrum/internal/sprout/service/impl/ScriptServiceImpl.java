package com.kickdrum.internal.sprout.service.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public boolean process(Script script) {
        //validate the script
        boolean validated = sqlParser.validateScript(script.getScriptData());
        if(!validated){
            return validated;
        }

        //persist the script
        save(script);

        //persist state and operation
        saveStateAndOperation(script);

        return true;
    }

    private void saveStateAndOperation(Script script){
        //set database.schema name
        sqlParser.setSchema("test");
        //set user id as modifier
        sqlParser.setModifier(2);

        List<StateOperationWrapper> stateOperationWrappers = sqlParser.parse(script.getScriptData());
        for(StateOperationWrapper wrapper : stateOperationWrappers){
            State state = wrapper.getState();
            state = stateService.save(state);
            for (Operation operation : wrapper.getOperations()){
                operation.setStateId(state.getId());
                operation.setScriptId(script.getId());
                operationService.save(operation);
            }
        }
    }
}
