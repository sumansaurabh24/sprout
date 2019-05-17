package com.kickdrum.internal.sprout.resource.v1;

import com.kickdrum.internal.sprout.entity.Project;
import com.kickdrum.internal.sprout.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService service;

    @GetMapping("")
    public String addProjectPage(Model model){
        return "add-project";
    }

    @PostMapping(value = "/save")
    public String save(Project project, Model model){
        boolean success = false;

        Project savedProject = service.save(project);
        if(savedProject.getId() != 0){
            success = true;
        }

        model.addAttribute("success", success);
        return "add-project";
    }

    @GetMapping("/list")
    public String listProjects(Model model){
        List<Project> projects = service.findAll();
        model.addAttribute("projects", projects);
        return "list-project";
    }
}
