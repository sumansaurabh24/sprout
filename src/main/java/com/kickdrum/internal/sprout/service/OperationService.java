package com.kickdrum.internal.sprout.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kickdrum.internal.sprout.entity.Operation;

@Service
public interface OperationService {

	Operation save(Operation state);

	List<Operation> findByStateIdAndGroupedByScriptId(Integer stateId);

	List<Operation> findByStateIdAndScriptId(Integer stateId, Integer scriptId);
}
