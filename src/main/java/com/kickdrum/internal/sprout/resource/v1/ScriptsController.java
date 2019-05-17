package com.kickdrum.internal.sprout.resource.v1;

import com.kickdrum.internal.sprout.entity.Project;
import com.kickdrum.internal.sprout.entity.Script;
import com.kickdrum.internal.sprout.service.ProjectService;
import com.kickdrum.internal.sprout.service.ScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        return "add-script";
    }


    @PostMapping(value = "/save", consumes = {"multipart/form-data"})
    public String saveScript(@RequestParam MultipartFile file, Script script, Model model) {
        boolean success = true;
        try {
            script.setScriptData(new String(file.getBytes()));
        } catch (IOException e) {
            success = false;
        }

        if(success){
            success = scriptService.process(script);
        }

        model.addAttribute("success", success);
        return "add-script";
    }

    
}
