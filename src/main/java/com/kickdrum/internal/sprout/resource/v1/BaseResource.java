package com.kickdrum.internal.sprout.resource.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kickdrum.internal.sprout.util.SqlParser;

@RestController
@RequestMapping("/api/v1")
public class BaseResource {

    @Autowired
    private SqlParser sqlParser;

    @GetMapping("/ping")
    public String ping() {
        return "I am alive";
    }


//    public static void main(String[] args) throws JSQLParserException {
//
//        String query = "alter table t1 add column city varchar(255), add column country varchar(100), drop column name";
//        SqlParser parser = new SqlParser();
//        List<Statement> statements = parser.validateScript(query);
//        List<StateOperationWrapper> wrappers = parser.parse(statements);
//    }
}
