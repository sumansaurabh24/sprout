/**
 * 
 */
package com.kickdrum.internal.sprout.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kickdrum.internal.sprout.entity.Server;

/**
 * @author gowrish
 *
 */
@Repository
public interface ServerDao extends JpaRepository<Server, Long> {

	@Query(value = "SELECT table_schema,table_name, GROUP_CONCAT(column_name) as \"columns\" FROM INFORMATION_SCHEMA.COLUMNS where table_schema='sprout' group by table_name", nativeQuery = true)
	List<Map<String, Object>> fetchInitState();

}
