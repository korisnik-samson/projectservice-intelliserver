package com.samson.projectserviceintelliserver.models;

import com.samson.projectserviceintelliserver.lib.ProjectStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "_projects")
public class Project {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_id")
    private Long id;
    
    @Column(name = "project_name")
    private String projectName;
    
    @Column(name = "project_description")
    private String projectDescription;
    
    @Column(name = "start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;
    
    @Column(name = "end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;
    
    @Column(name = "project_status")
    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;
    
}
