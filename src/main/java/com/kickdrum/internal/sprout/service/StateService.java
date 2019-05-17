package com.kickdrum.internal.sprout.service;

import com.kickdrum.internal.sprout.entity.State;
import org.springframework.stereotype.Service;

@Service
public interface StateService {

    State save(State state);
}
