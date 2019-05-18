package com.kickdrum.internal.sprout.resource.v1;

import com.kickdrum.internal.sprout.entity.Operation;
import com.kickdrum.internal.sprout.entity.State;
import com.kickdrum.internal.sprout.service.OperationService;
import com.kickdrum.internal.sprout.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/schema")
public class SchemaController {

    @Autowired
    private StateService stateService;

    @Autowired
    private OperationService operationService;

    @GetMapping("/version")
    public String displaySchemaVersion(@RequestParam(value = "id", required = false) Integer stateId, Model model) {
        List<State> states = stateService.findAll();
        model.addAttribute("states", states);

        if (stateId == null) {
            return "schema-version";
        }

        List<Operation> operations = operationService.findByStateIdAndGroupedByScriptId(stateId);
        List<List<Operation>> listOfOperationList = new ArrayList<>();
        for (Operation operation : operations) {
            listOfOperationList.add(operationService.findByStateIdAndScriptId(operation.getStateId(), operation.getScriptId()));
        }
        if (operations != null && operations.size() != 0) {
            State state = stateService.findById(stateId);
            model.addAttribute("state", state);
            model.addAttribute("listOfOperationList", listOfOperationList);
        }
        return "schema-version";
    }
}
