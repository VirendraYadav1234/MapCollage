package com.mapCollage.Security.Model;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String email;
    private String password;  // Add this field
    private String role;
    private Boolean enabled;
    private Boolean accountLocked;
    
    // getters and setters
}