package com.samson.projectserviceintelliserver.services;

import com.samson.projectserviceintelliserver.lib.Utils;
import com.samson.projectserviceintelliserver.models.Project;
import com.samson.projectserviceintelliserver.repositories.ProjectRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.samson.projectserviceintelliserver.lib.ProjectStatus;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    
    private final ProjectRepository projectRepository;
    private final DependencyService dependencyService;
    
    @Autowired
    public ProjectService(ProjectRepository projectRepository, DependencyService dependencyService) {
        this.projectRepository = projectRepository;
        this.dependencyService = dependencyService;
    }

    public Project createProject(@NonNull Project project) throws URISyntaxException {
        // logic for verification of the user as an admin from DependencyService
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        
        project.setStartDate(LocalDateTime.parse(formatter.format(now), formatter));
        
        if (!Utils.verifyStatus(project.getProjectStatus())) project.setProjectStatus(ProjectStatus.PENDING);
        
        // obtain the creator id from the DependencyService
        project.setCreatedBy(this.dependencyService.getProjectCreatorUsername(project.getCreatedBy()));
        
        return this.projectRepository.save(project);
    }

    public Optional<Project> getProjectById(Long id) {
        return this.projectRepository.findById(id);
    }

    public List<Project> getAllProjects() {
        return this.projectRepository.findAll();
    }

    public Optional<List<Project>> getProjectsByOwner(String owner) {
        return Optional.empty();
    }

    public Project updateProject(Long projectId, Project updateFields) {
        
        return this.projectRepository.findById(projectId).map(project -> {
            if (updateFields.getProjectName() != null) project.setProjectName(updateFields.getProjectName());
            if (updateFields.getProjectDescription() != null) project.setProjectDescription(updateFields.getProjectDescription());
            if (updateFields.getStartDate() != null) project.setStartDate(updateFields.getStartDate());
            if (updateFields.getEndDate() != null) project.setEndDate(updateFields.getEndDate());
            if (updateFields.getProjectStatus() != null) project.setProjectStatus(updateFields.getProjectStatus());
            if (updateFields.getCreatedBy() != null) project.setCreatedBy(updateFields.getCreatedBy());
            
            return this.projectRepository.save(project);
            
        }).orElse(null);
    }

    public void deleteProject(Long projectId) {
        this.projectRepository.deleteById(projectId);
    }

    public Project addUserToProject(Long projectId, String username) {
        // verify the user as an ADMIN from the DependencyService before
        
        return null;
    }

    public Project removeUserFromProject(Long projectId, String username) {
        return null;
    }
}
