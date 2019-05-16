package com.kickdrum.internal.sprout.resources.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String indexPage(Model model){
        model.addAttribute("name", "Suman");
        return "index";
    }
}
