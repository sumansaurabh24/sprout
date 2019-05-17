package com.kickdrum.internal.sprout.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kickdrum.internal.sprout.entity.Script;

@Repository
public interface ScriptDao extends JpaRepository<Script, Long> {


}
