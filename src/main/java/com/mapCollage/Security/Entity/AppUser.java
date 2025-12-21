package com.mapCollage.Security.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "APP_USERS")
@Data
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ENABLED")
    private String enabled;

    @Column(name = "ACCOUNT_LOCKED")
    private String accountLocked;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;
    
    @Column(name="PASSWORD")
    private String password;
    
    @Column(name="ROLE")
    private String role;
}
