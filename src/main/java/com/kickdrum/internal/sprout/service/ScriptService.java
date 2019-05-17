package com.kickdrum.internal.sprout.service;

import com.kickdrum.internal.sprout.entity.Script;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScriptService {

    Script findById(Long id);
    Script save(Script scriptInput);
    List<Script> findAll();
}
