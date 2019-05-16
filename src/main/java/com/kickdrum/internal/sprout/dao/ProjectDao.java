package com.kickdrum.internal.sprout.dao;

import com.kickdrum.internal.sprout.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDao extends JpaRepository<Project, Long> {


}
