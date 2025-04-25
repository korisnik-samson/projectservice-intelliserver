package com.samson.projectserviceintelliserver.services;

import com.samson.projectserviceintelliserver.models.Users;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Service
public class DependencyService {
    
    private final RestTemplate restTemplate;
    private final URI baseUrl;
    
    @Autowired
    public DependencyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.baseUrl = URI.create("http://userservice.westeurope.cloudapp.azure.com/");
    }
    
    @NonNull
    private URI addPath(URI uri, @NonNull String path) {
        String newPath;
        
        if (path.startsWith("/")) newPath = path.replaceAll("//+", "/");
        else if (uri.getPath().endsWith("/")) newPath = uri.getPath() + path.replaceAll("//+", "/");
        else newPath = uri.getPath() + "/" + path.replaceAll("//+", "/");

        return uri.resolve(newPath).normalize();
    }
    
    private String getAuthToken(String username, String password) throws URISyntaxException {
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        RequestEntity <String> loginRequest = new RequestEntity<>(
                String.format("{\"username\":\"%s\", \"password\":\"%s\"}", username, password),
                headers,
                HttpMethod.POST,
                addPath(this.baseUrl, "login")
        );

        ResponseEntity<String> response = restTemplate.exchange(loginRequest, String.class);

        return response.getBody();
    }

    public boolean verifyRole(String username, String password, String role) throws URISyntaxException {
        // logic for verifying the user as an admin
        
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getAuthToken(username, password));
        
        HttpEntity<Void> request = new HttpEntity<>(headers);
        
        ResponseEntity<List<Users>> response = restTemplate.exchange(
                addPath(this.baseUrl, "api/login"), HttpMethod.GET, request,
                new ParameterizedTypeReference<>() {}
        );
        
        List<Users> usersList = response.getBody();
        
        if (usersList == null) return false;

        Optional<Users> users = usersList.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
        
        return users.isPresent() && users.get().getRole().contains(role);
    }

    
    public String getProjectCreatorUsername(String token) throws URISyntaxException {
        return extractUsernameFromRequest(token);
    }
    
    @NonNull
    private String extractUsernameFromRequest(String token) throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        String requestBody = String.format("{\"token\":\"%s\"}", token);
        
        RequestEntity<String> request = new RequestEntity<>(
                requestBody,
                headers,
                HttpMethod.POST,
                addPath(this.baseUrl, "de-token")
        );
        
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null)
            return response.getBody();
        else throw new RuntimeException("Failed to extract username from request");
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
