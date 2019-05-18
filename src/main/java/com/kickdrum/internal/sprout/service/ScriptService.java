package com.kickdrum.internal.sprout.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kickdrum.internal.sprout.entity.Script;
import com.kickdrum.internal.sprout.exception.SproutException;

import net.sf.jsqlparser.JSQLParserException;

@Service
public interface ScriptService {

	Script findById(Long id);

	Script save(Script scriptInput);

	List<Script> findAll();

	void process(Script script) throws JSQLParserException, SproutException;

	List<String> listSequence();
}
