/**
 * 
 */
package com.kickdrum.internal.sprout.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kickdrum.internal.sprout.entity.Server;

/**
 * @author gowrish
 *
 */
@Service
public interface ServerService {
	public List<Server> getConfigs();

	public List<Map<String, Object>> getInitState();
	
	
}
