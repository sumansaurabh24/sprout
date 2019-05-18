package com.kickdrum.internal.sprout.util;

import com.kickdrum.internal.sprout.entity.Operation;
import com.kickdrum.internal.sprout.entity.State;
import com.kickdrum.internal.sprout.enums.StateOperation;
import com.kickdrum.internal.sprout.model.StateOperationWrapper;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AppUtil {

    public static StateOperationWrapper modifyStateColumnList(StateOperationWrapper wrapper, State existingState){
        //pull state from db
        String existingColumns = existingState.getColumns();
        List<String> columnList = new LinkedList<>(Arrays.asList(existingColumns.split(",")));

        for(Operation operation : wrapper.getOperations()){
            columnList.removeIf(op ->
                    (columnList.contains(operation.getAffectedColumn())
                            && operation.getOperation().equalsIgnoreCase(StateOperation.DROP.name()))
            );

            if(operation.getOperation().equalsIgnoreCase(StateOperation.ADD.name())){
                columnList.add(operation.getAffectedColumn());
            }
        }
        String modifiedColumn = StringUtils.join(columnList, ",");

        existingState.setColumns(modifiedColumn);
        wrapper.setState(existingState);
        return wrapper;
    }
}
