package com.kickdrum.internal.sprout.dao;

import com.kickdrum.internal.sprout.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationDao extends JpaRepository<Operation, Integer> {


}
