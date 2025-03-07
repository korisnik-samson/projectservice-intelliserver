package com.samson.projectserviceintelliserver.services;

import com.samson.projectserviceintelliserver.models.Project;
import com.samson.projectserviceintelliserver.repositories.ProjectRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.samson.projectserviceintelliserver.lib.ProjectStatus;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    
    private final ProjectRepository projectRepository;
    
    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(@NonNull Project project) {
        // logic for verification of the user as an admin from DependencyService
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        
        project.setStartDate(LocalDateTime.parse(formatter.format(now), formatter));
        
        //Optional<ProjectStatus> optionalProjectStatus = Optional.ofNullable(project.getProjectStatus());
        
        //optionalProjectStatus.ifPresent(project::setProjectStatus);
        
        if (project.getProjectStatus() == null) project.setProjectStatus(ProjectStatus.PENDING);
        
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
            
            return this.projectRepository.save(project);
            
        }).orElse(null);
    }

    public void deleteProject(Long projectId) {
        this.projectRepository.deleteById(projectId);
    }

    public Project addUserToProject(Long projectId, String username) {
        return null;
    }

    public Project removeUserFromProject(Long projectId, String username) {
        return null;
    }
}
