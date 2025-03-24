package com.samson.projectserviceintelliserver.models;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Users {

    private Long user_id;
    
    private String firstName;
    
    private String lastName;
    
    private String username;
    
    private String email;
    
    private String password;
    
    private String role;
    
}
