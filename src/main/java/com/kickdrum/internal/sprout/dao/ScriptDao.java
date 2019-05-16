package com.kickdrum.internal.sprout.dao;

import com.kickdrum.internal.sprout.entity.Script;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScriptDao extends JpaRepository<Script, Long> {


}
