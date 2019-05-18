package com.kickdrum.internal.sprout.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kickdrum.internal.sprout.entity.State;

@Repository
public interface StateDao extends JpaRepository<State, Integer> {

	@Query(value = "SELECT * FROM state where schema_name = :schema and table_name = :table and id= (select max(id) from state where table_name=:table)", nativeQuery = true)
	State findBySchemaAndTable(@Param("schema") String schema, @Param("table") String table);

	@Query(value = "SELECT  * FROM state WHERE id IN (SELECT MAX(id) AS id FROM state GROUP BY table_name)", nativeQuery = true)
	List<State> findLatestState();
}
