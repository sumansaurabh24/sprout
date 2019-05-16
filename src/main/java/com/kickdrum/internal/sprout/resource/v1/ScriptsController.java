package com.kickdrum.internal.sprout.resource.v1;

import com.kickdrum.internal.sprout.entity.Project;
import com.kickdrum.internal.sprout.entity.Script;
import com.kickdrum.internal.sprout.service.ProjectService;
import com.kickdrum.internal.sprout.service.ScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/scripts")
public class ScriptsController {

    @Autowired
    private ScriptService scriptService;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/add")
    public String addScriptsPage(Model model) {
        List<Project> projects = projectService.findAll();
        model.addAttribute("projects", projects);
        return "add-scripts";
    }

    @PostMapping("/save")
    public String saveScript(Model model) {
        //save into db
        //based on response you need to send message to
        model.addAttribute("succes", "true");
        return "add-script";
    }

    @GetMapping(path = "/{id}")
    public Script findById(@PathVariable("id") Long id) {
        return scriptService.findById(id);
    }
}
