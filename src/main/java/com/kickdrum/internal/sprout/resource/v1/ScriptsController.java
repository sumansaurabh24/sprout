package com.kickdrum.internal.sprout.resource.v1;

import java.io.IOException;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kickdrum.internal.sprout.entity.Project;
import com.kickdrum.internal.sprout.entity.Script;
import com.kickdrum.internal.sprout.entity.Sprint;
import com.kickdrum.internal.sprout.exception.SproutException;
import com.kickdrum.internal.sprout.service.ProjectService;
import com.kickdrum.internal.sprout.service.ScriptService;

import net.sf.jsqlparser.JSQLParserException;

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
//		List<Sprint> sprints = projectService.findAllSprints();
//		model.addAttribute("sprints", sprints);
		model.addAttribute("scriptsInOrder",scriptService.listSequence());
		return "add-script";
	}

	@PostMapping(value = "/save", consumes = { "multipart/form-data" })
	public String saveScript(@RequestParam MultipartFile file, Script script, Model model) {
		boolean success = true;
		String errorMessage = null;
		try {
			script.setScriptData(new String(file.getBytes()));
		} catch (IOException e) {
			success = false;
			errorMessage = "Please upload SQL file to process";
		}

		try {
			scriptService.process(script);
		} catch (JSQLParserException e) {
			e.printStackTrace();
			success = false;
			errorMessage = "Your script has invalid sql statements. Please fix and re upload";

		} catch (SproutException e) {
			e.printStackTrace();
			success = false;
			errorMessage = e.getMessage();
		}

		model.addAttribute("success", success);
		model.addAttribute("errorMessage", errorMessage);
		model.addAttribute("projects", projectService.findAll());
		model.addAttribute("scriptsInOrder",scriptService.listSequence());
		return "add-script";
	}

	@GetMapping("/dependencies")
	@ResponseBody
	public ResponseEntity<List<Script>> initState() {
		return ResponseEntity.ok().body(scriptService.listSequence());
	}

	@GetMapping("/project/{projectId}/sprints")
	@ResponseBody
	public ResponseEntity<List<Sprint>> getSprints(@PathVariable("projectId") Integer projectId) {
		return ResponseEntity.ok().body(projectService.findAllSprints(projectId));
	}

}
