package com.kickdrum.internal.sprout.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kickdrum.internal.sprout.entity.Script;

@Service
public interface ScriptService {

    Script findById(Long id);
    Script save(Script scriptInput);
    List<Script> findAll();
    boolean process(Script script);
}
