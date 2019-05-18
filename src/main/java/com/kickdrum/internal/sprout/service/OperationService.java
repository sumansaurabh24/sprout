package com.kickdrum.internal.sprout.service;

import com.kickdrum.internal.sprout.entity.Operation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OperationService {

    Operation save(Operation state);

    List<Operation> findByStateIdAndGroupedByScriptId(Integer stateId);

    List<Operation> findByStateIdAndScriptId(Integer stateId, Integer scriptId);
}
