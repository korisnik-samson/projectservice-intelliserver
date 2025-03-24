package com.samson.projectserviceintelliserver.lib;

import com.samson.projectserviceintelliserver.models.Project;
import com.samson.projectserviceintelliserver.models.Users;
import lombok.NonNull;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Utils {
    
    public static boolean verifyStatus(ProjectStatus status) {
        return status == ProjectStatus.ACTIVE || status == ProjectStatus.INACTIVE || status == ProjectStatus.COMPLETED;
    }
    
    public static UUID generateUUID(@NonNull Integer id) {
        return UUID.fromString(String.format("00000000-0000-0000-0000-%012d", id));
    }

    public static String getToken(String username, String password) throws URISyntaxException {
        URI url = new URI("http://userservice.westeurope.cloudapp.azure.com/login");
        
        RestTemplate restTemplate = new RestTemplate();
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        RequestEntity<String> loginRequest = new RequestEntity<>(
                String.format("{\"username\":\"%s\", \"password\":\"%s\"}", username, password),
                headers,
                HttpMethod.POST,
                url
        );
        
        ResponseEntity<String> response = restTemplate.exchange(loginRequest, String.class);
        
        return response.getBody();
    }

    public static boolean verifyAdmin(String username, String password) throws URISyntaxException {
        // logic for verifying the user as an admin
        URI url = new URI("http://userservice.westeurope.cloudapp.azure.com/api/users");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getToken(username, password));

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<List<Users>> response = new RestTemplate().exchange(
                url, HttpMethod.GET, request,
                new ParameterizedTypeReference<>() {}
        );

        List<Users> usersList = response.getBody();

        if (usersList == null) return false;

        Optional<Users> users = usersList.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();

        return users.isPresent() && users.get().getRole().contains("MEMBER");
    }
    
    public static void main(String[] args) throws URISyntaxException {
        System.out.println(generateUUID(1));
    }
    
}
