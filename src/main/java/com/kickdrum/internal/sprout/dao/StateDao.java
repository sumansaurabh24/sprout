package com.kickdrum.internal.sprout.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kickdrum.internal.sprout.entity.State;

@Repository
public interface StateDao extends JpaRepository<State, Integer> {

	@Query("select state from State state where state.schema = ?1 and state.table = ?2")
	State findBySchemaAndTable(String schema, String table);

	@Query(value = "SELECT  * FROM state WHERE id IN (SELECT MAX(id) AS id FROM state GROUP BY table_name)", nativeQuery = true)
	List<State> findLatestState();
}
