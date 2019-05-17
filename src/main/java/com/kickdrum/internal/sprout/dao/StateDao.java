package com.kickdrum.internal.sprout.dao;

import com.kickdrum.internal.sprout.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateDao extends JpaRepository<State, Integer> {


}
