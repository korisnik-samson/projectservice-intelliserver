package com.samson.projectserviceintelliserver.repositories;

import com.samson.projectserviceintelliserver.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    //@Query("SELECT * FROM Project p WHERE p.owner = ?1")
    public Optional<List<Project>> findByOwner(String owner);
    
}
