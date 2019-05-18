package com.kickdrum.internal.sprout.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kickdrum.internal.sprout.dao.ProjectDao;
import com.kickdrum.internal.sprout.entity.Project;
import com.kickdrum.internal.sprout.service.ProjectService;

import lombok.extern.slf4j.Slf4j;
import com.kickdrum.internal.sprout.entity.Sprint;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;

    @Override
    public Project findById(Long id) {
        return null;
    }

    @Override
    public Project save(Project project) {
        return projectDao.save(project);
    }

    @Override
    public List<Project> findAll() {
        final String uri = "https://7hrtasvpbb.execute-api.ap-south-1.amazonaws.com/dev/projects";
        RestTemplate restTemplate = new RestTemplate();


        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        ResponseEntity<List<Project>> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Project>>() {
                        });

        List<Project> projects = responseEntity.getBody();
        return projects;
    }

    @Override
    public List<Sprint> findAllSprints(){
        final String uri = "https://7hrtasvpbb.execute-api.ap-south-1.amazonaws.com/dev/projects/2/sprints";
        RestTemplate restTemplate = new RestTemplate();


        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        ResponseEntity<List<Sprint>> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Sprint>>() {
                        });

        List<Sprint> sprints = responseEntity.getBody();
        return sprints;
    }
}
