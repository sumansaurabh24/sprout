package com.kickdrum.internal.sprout.service.impl;

import com.kickdrum.internal.sprout.dao.ScriptDao;
import com.kickdrum.internal.sprout.entity.Script;
import com.kickdrum.internal.sprout.service.ScriptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScriptServiceImpl implements ScriptService {

    @Autowired
    private ScriptDao scriptDao;

    @Override
    public Script findById(Long id) {
        return scriptDao.findById(id).orElse(null);
    }
}
