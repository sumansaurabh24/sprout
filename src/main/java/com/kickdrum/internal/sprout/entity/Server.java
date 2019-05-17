/**
 * 
 */
package com.kickdrum.internal.sprout.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author gowrish
 *
 */
@Entity
@Data
public class Server {

	@Id
	private Integer id;
	private String host;
	private String port;
	private String dbUsername;
	private String dbPassword;
	private String dbIdentifier;
	private int projectId;
	private String env;

}
