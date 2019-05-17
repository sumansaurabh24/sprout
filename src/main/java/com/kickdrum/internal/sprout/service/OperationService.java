package com.kickdrum.internal.sprout.service;

import com.kickdrum.internal.sprout.entity.Operation;
import org.springframework.stereotype.Service;

@Service
public interface OperationService {

    Operation save(Operation state);
}
