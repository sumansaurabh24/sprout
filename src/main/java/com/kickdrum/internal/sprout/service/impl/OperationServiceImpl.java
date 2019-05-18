package com.kickdrum.internal.sprout.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kickdrum.internal.sprout.dao.OperationDao;
import com.kickdrum.internal.sprout.entity.Operation;
import com.kickdrum.internal.sprout.service.OperationService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OperationServiceImpl implements OperationService {

    @Autowired
    private OperationDao operationDao;

    @Override
    public Operation save(Operation operation) {
        return operationDao.save(operation);
    }

    @Override
    public List<Operation> findByStateIdAndGroupedByScriptId(Integer stateId) {
        return operationDao.findByStateIdAndGroupedByScriptId(stateId);
    }

    @Override
    public List<Operation> findByStateIdAndScriptId(Integer stateId, Integer scriptId) {
        return  operationDao.findByStateIdAndScriptId(stateId, scriptId);
    }
}
