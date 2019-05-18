package com.kickdrum.internal.sprout.resource.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BaseResource {

    @GetMapping("/ping")
    public String ping() {
        return "I am alive";
    }

}
