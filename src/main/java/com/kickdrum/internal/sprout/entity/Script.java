package com.kickdrum.internal.sprout.entity;

import lombok.Data;
import org.hibernate.annotations.Generated;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Script {

    @Id
    private long id;

    private String title;
    private String jiraId;
    private String script;
    private String sprint;
    private long projectId;
}
