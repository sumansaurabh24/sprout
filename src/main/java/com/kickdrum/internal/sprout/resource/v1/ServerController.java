/**
 * 
 */
package com.kickdrum.internal.sprout.resource.v1;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kickdrum.internal.sprout.entity.Server;
import com.kickdrum.internal.sprout.service.ServerService;

/**
 * @author gowrish
 *
 */
@RestController
@RequestMapping("/servers")
public class ServerController {

	@Autowired
	ServerService serverService;

	@GetMapping()
	public ResponseEntity<List<Server>> setupTablesState() {
		return ResponseEntity.ok().body(serverService.getConfigs());
	}

	@GetMapping("/init-state")
	public ResponseEntity<List<Map<String, Object>>> initState() {
		return ResponseEntity.ok().body(serverService.getInitState());
	}
}
