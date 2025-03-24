package com.samson.projectserviceintelliserver.repositories;

import com.samson.projectserviceintelliserver.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
}
