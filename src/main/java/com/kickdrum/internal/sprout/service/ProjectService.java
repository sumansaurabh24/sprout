package com.kickdrum.internal.sprout.service;

import com.kickdrum.internal.sprout.entity.Project;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectService {

    Project findById(Long id);
    Project save(Project project);
    List<Project> findAll();
}
