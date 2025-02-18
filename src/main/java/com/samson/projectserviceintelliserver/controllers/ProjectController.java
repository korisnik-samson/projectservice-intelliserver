package com.samson.projectserviceintelliserver.controllers;

import com.samson.projectserviceintelliserver.models.Project;
import com.samson.projectserviceintelliserver.services.ProjectService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Controller
public class ProjectController {
    
    private final ProjectService projectService;
    
    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    
    @PostMapping(path = "api/projects")
    public Project createProject(@RequestBody Project project) {
        return this.projectService.createProject(project);
    }
    
    @GetMapping(path = "api/project/{id}")
    public Optional<Project> getProjectById(@PathVariable("id") Long id) {
        return this.projectService.getProjectById(id);
    }
    
    @GetMapping(path = "api/projects")
    public List<Project> getAllProjects() {
        return this.projectService.getAllProjects();
    }
    
    @GetMapping(path = "api/projects/{owner}")
    public Optional<List<Project>> getProjectsByOwner(@PathVariable("owner") String owner) {
        return this.projectService.getProjectsByOwner(owner);
    }
    
    @PatchMapping(path = "api/projects/{id}")
    public Project updateProject(@PathVariable("id") Long projectId, @RequestBody Project updateFields) {
        return this.projectService.updateProject(projectId, updateFields);
    }
    
    @DeleteMapping(path = "api/projects/{id}")
    public void deleteProject(@PathVariable("id") Long projectId) {
        this.projectService.deleteProject(projectId);
    }
    
    @PostMapping(path = "api/projects/{id}/user")
    public Project addUserToProject(@PathVariable("id") Long projectId, @RequestBody String username) {
        return this.projectService.addUserToProject(projectId, username);
    }
    
    @DeleteMapping(path = "api/projects/{id}/user")
    public Project removeUserFromProject(@PathVariable("id") Long projectId, @RequestBody String username) {
        return this.projectService.removeUserFromProject(projectId, username);
    }
    
}
