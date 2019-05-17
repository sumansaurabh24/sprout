package com.kickdrum.internal.sprout.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kickdrum.internal.sprout.dao.ScriptDao;
import com.kickdrum.internal.sprout.entity.Script;
import com.kickdrum.internal.sprout.service.ScriptService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ScriptServiceImpl implements ScriptService {

    @Autowired
    private ScriptDao scriptDao;

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
}
