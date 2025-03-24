package com.samson.projectserviceintelliserver.services;

import com.samson.projectserviceintelliserver.models.Users;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class DependencyService {
    
    private final RestTemplate restTemplate;
    
    @Autowired
    public DependencyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    private String getAuthToken(String username, String password) throws URISyntaxException {
        URI url = new URI("http://userservice.westeurope.cloudapp.azure.com/lgoin");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        RequestEntity <String> loginRequest = new RequestEntity<>(
                String.format("{\"username\":\"%s\", \"password\":\"%s\"}", username, password),
                headers,
                HttpMethod.POST,
                url
        );

        ResponseEntity<String> response = restTemplate.exchange(loginRequest, String.class);

        return response.getBody();
    }

    public boolean verifyRole(String username, String password, String role) throws URISyntaxException {
        // logic for verifying the user as an admin
        URI url = new URI("http://userservice.westeurope.cloudapp.azure.com/api/users");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getAuthToken(username, password));
        
        HttpEntity<Void> request = new HttpEntity<>(headers);
        
        ResponseEntity<List<Users>> response = restTemplate.exchange(
                url, HttpMethod.GET, request,
                new ParameterizedTypeReference<>() {}
        );
        
        List<Users> usersList = response.getBody();
        
        if (usersList == null) return false;

        Optional<Users> users = usersList.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
        
        return users.isPresent() && users.get().getRole().contains(role);
    }
    
    public boolean verifyUser(String username) {
        // logic for verifying the user
        return true;
    }
    
    public boolean verifyProjectOwner(String username, String password, Long projectId) throws URISyntaxException {
        // logic for verifying the user as the owner of the project
        
        boolean isAdmin = verifyRole(username, password, "ADMIN");
        
        
        
        return true;
    }
    
    public boolean verifyProjectParticipant(String username, Long projectId) {
        // logic for verifying the user as a participant of the project
        return true;
    }
    
    public boolean verifyProjectStatus(Long projectId) {
        // logic for verifying the status of the project
        return true;
    }
    
    public boolean verifyProjectDeadline(Long projectId) {
        // logic for verifying the deadline of the project
        return true;
    }
    
    public boolean verifyProjectStartDate(Long projectId) {
        // logic for verifying the start date of the project
        return true;
    }
    
    public boolean verifyProjectEndDate(Long projectId) {
        // logic for verifying the end date of the project
        return true;
    }
    
    public boolean verifyProjectStatusChange(Long projectId) {
        // logic for verifying the change of the project status
        return true;
    }
    
    public boolean verifyProjectStartDateChange(Long projectId) {
        // logic for verifying the change of the project start date
        return true;
    }
    
    public boolean verifyProjectEndDateChange(Long projectId) {
        // logic for verifying the change of the project end date
        return true;
    }
    
    public boolean verifyProjectParticipantAddition(Long projectId) {
        // logic for verifying the addition of a participant to the project
        return true;
    }
    
    public boolean verifyProjectParticipantRemoval(Long projectId) {
        // logic for verifying the removal of a participant from the project
        return true;
    }
    
    public boolean verifyProjectUserAddition(Long projectId) {
        // logic for verifying the addition of a user to the project
        return true;
    }
    
    public boolean verifyProjectUserRemoval(Long projectId) {
        // logic for verifying the removal of a user from the project
        return true;
    }
    
    public boolean verifyProjectDeletion(Long projectId) {
        // logic for verifying the deletion of the project
        return true;
    }
    
    public boolean verifyProjectCreation() {
        // logic for verifying the creation of the project
        return true;
    }
    
}
