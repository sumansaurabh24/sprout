package com.kickdrum.internal.sprout.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kickdrum.internal.sprout.entity.Operation;

@Repository
public interface OperationDao extends JpaRepository<Operation, Integer> {

    @Query("select op from Operation op where op.stateId = ?1 group by scriptId")
    List<Operation> findByStateIdAndGroupedByScriptId(Integer stateId);

    @Query("select op from Operation op where stateId = ?1 and scriptId = ?2")
    List<Operation> findByStateIdAndScriptId(Integer stateId, Integer scriptId);
}
