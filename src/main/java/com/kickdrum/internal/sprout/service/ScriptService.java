package com.kickdrum.internal.sprout.service;

import com.kickdrum.internal.sprout.entity.Script;
import org.springframework.stereotype.Service;

@Service
public interface ScriptService {

    Script findById(Long id);
}