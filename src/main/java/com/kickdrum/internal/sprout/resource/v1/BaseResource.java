package com.kickdrum.internal.sprout.resource.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kickdrum.internal.sprout.entity.State;
import com.kickdrum.internal.sprout.service.StateService;
import com.kickdrum.internal.sprout.util.SqlParser;

@RestController
@RequestMapping("/api/v1")
public class BaseResource {

    @Autowired
    private SqlParser parser;

    @Autowired
    private StateService stateService;

    @GetMapping("/ping")
    public String ping() {
        return "I am alive";
    }

    @GetMapping("/sql/parse")
    public void parse() {
        parser.setSchema("test");
        parser.setModifier(2);
        List<State> states = parser.parse("create table t1 (name varchar(255),userId Integer,FOREIGN KEY(name) REFERENCES user(name),FOREIGN KEY(userId) REFERENCES user(id))");
        for(State state : states){
            System.out.println(state.getSchema());
            state.setScriptId(1);
            stateService.save(state);
        }
    }

//    public static void main(String[] args) {
//
//        SqlParser parser = new SqlParser();
//        parser.parse("create table t1 (name varchar(255),userId Integer,FOREIGN KEY(name) REFERENCES user(name),FOREIGN KEY(userId) REFERENCES user(id))");
//    }
}
