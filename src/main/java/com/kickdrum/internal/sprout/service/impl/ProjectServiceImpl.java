package com.kickdrum.internal.sprout.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kickdrum.internal.sprout.dao.ProjectDao;
import com.kickdrum.internal.sprout.entity.Project;
import com.kickdrum.internal.sprout.service.ProjectService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;

    @Override
    public Project findById(Long id) {
        return null;
    }

    @Override
    public Project save(Project project) {
        return projectDao.save(project);
    }

    @Override
    public List<Project> findAll() {
        return projectDao.findAll();
    }
}
