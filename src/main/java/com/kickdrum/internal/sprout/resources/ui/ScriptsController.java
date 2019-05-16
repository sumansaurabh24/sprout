package com.kickdrum.internal.sprout.resources.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/scripts")
public class ScriptsController {

    @GetMapping("/add")
    public String addScriptsPage(Model model){
        return "add-scripts";
    }
}
