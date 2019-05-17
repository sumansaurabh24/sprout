package com.kickdrum.internal.sprout.service;

import org.springframework.stereotype.Service;

import com.kickdrum.internal.sprout.entity.State;

@Service
public interface StateService {

    State save(State state);
}
