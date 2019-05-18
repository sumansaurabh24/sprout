package com.kickdrum.internal.sprout.service;

import java.util.List;
import com.kickdrum.internal.sprout.entity.Project;
import com.kickdrum.internal.sprout.entity.Sprint;
import org.springframework.stereotype.Service;

@Service
public interface ProjectService {

    Project findById(Long id);
    Project save(Project project);
    List<Project> findAll();
    List<Sprint> findAllSprints();
}
