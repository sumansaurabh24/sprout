package com.kickdrum.internal.sprout.resource.v1;

import com.kickdrum.internal.sprout.model.StateOperationWrapper;
import com.kickdrum.internal.sprout.util.SqlParser;
import net.sf.jsqlparser.JSQLParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BaseResource {

    @Autowired
    private SqlParser sqlParser;

    @GetMapping("/ping")
    public String ping() {
        return "I am alive";
    }


    public static void main(String[] args) throws JSQLParserException {

        String query = "alter table t1 add column city varchar(255), add column country varchar(100), drop column name";
        SqlParser parser = new SqlParser();
        List<StateOperationWrapper> wrappers = parser.parse(query);

//        Alter alter = (Alter) CCJSqlParserUtil.parse(
//                "alter table t1 add column city varchar(255), add column country varchar(100), drop column name");
//        List<AlterExpression> expressions = alter.getAlterExpressions();
//        for (AlterExpression alterExpression : expressions) {
//            String operation = alterExpression.getOperation().name();
//            if (operation == "ADD") {
//                String columnName = alterExpression.getColDataTypeList().get(0).getColumnName();
//                String columnType = alterExpression.getColDataTypeList().get(0).getColDataType().getDataType();
//                System.out.println(operation + " : " + columnName + " : " + columnType);
//            } else {
//                String columName = alterExpression.getColumnName();
//                System.out.println(operation + " : " + columName);
//            }
//        }
    }
}
