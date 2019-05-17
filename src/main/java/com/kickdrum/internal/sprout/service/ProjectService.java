package com.kickdrum.internal.sprout.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kickdrum.internal.sprout.entity.Project;

@Service
public interface ProjectService {

    Project findById(Long id);
    Project save(Project project);
    List<Project> findAll();
}
