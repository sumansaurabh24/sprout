package com.kickdrum.internal.sprout.resource.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kickdrum.internal.sprout.entity.Project;
import com.kickdrum.internal.sprout.service.ProjectService;

@Controller
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@GetMapping("")
	public String addProjectPage(Model model) {
		return "add-project";
	}

	@PostMapping(value = "/save")
	public String save(Project project, Model model) {
		boolean success = false;

		Project savedProject = projectService.save(project);
		if (savedProject.getId() != 0) {
			success = true;
		}

		model.addAttribute("success", success);
		return "add-project";
	}
	@GetMapping("/list")
	public String listProjects(Model model) {
		List<Project> projects = projectService.findAll();
		model.addAttribute("projects", projects);
		return "list-project";
	}
}
