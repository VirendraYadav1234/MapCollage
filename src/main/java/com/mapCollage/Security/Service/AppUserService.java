package com.mapCollage.Security.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mapCollage.Security.Entity.AppUser;
import com.mapCollage.Security.JdbcRepository.AppUserJdbcRepository;
import com.mapCollage.Security.Model.UserPatchRequest;
import com.mapCollage.Security.Model.UserRequest;
import com.mapCollage.Security.Repositry.AppUserRepository;


@Service
public class AppUserService {

    private final AppUserRepository jpaRepo;
    private final AppUserJdbcRepository jdbcRepo;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository jpaRepo,
                          AppUserJdbcRepository jdbcRepo,
                          PasswordEncoder passwordEncoder) {
        this.jpaRepo = jpaRepo;
        this.jdbcRepo = jdbcRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // CREATE
    @Transactional
    public AppUser createUser(UserRequest request) {
        // Check if username already exists
        if (jpaRepo.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists: " + request.getUsername());
        }

        // Check if email already exists
        if (jpaRepo.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }

        AppUser user = new AppUser();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setEnabled(request.getEnabled() != null && request.getEnabled() ? "Y" : "N");
        user.setAccountLocked(request.getAccountLocked() != null && request.getAccountLocked() ? "Y" : "N");
        
        // ENCRYPT PASSWORD BEFORE SAVING
        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encryptedPassword);
        
        user.setRole(request.getRole() != null ? request.getRole() : "USER");
        user.setUpdatedAt(LocalDateTime.now());

        return jpaRepo.save(user);
    }

    // GET
    public AppUser getUser(Long userId) {
        return jdbcRepo.getUser(userId);
    }

    public List<AppUser> getAllUsers() {
        return jpaRepo.findAll();
    }

    // PATCH - Update user including password
    @Transactional
    public AppUser patchUser(Long userId, UserPatchRequest request) {
        AppUser user = jpaRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Business rules
        if ("admin".equals(user.getUsername()) && request.getEnabled() != null && !request.getEnabled()) {
            throw new RuntimeException("Admin cannot be disabled");
        }

        // If password is being updated, encrypt it first
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            String encryptedPassword = passwordEncoder.encode(request.getPassword());
            request.setPassword(encryptedPassword);
        }

        int updated = jdbcRepo.patchUser(userId, request);
        if (updated == 0) throw new RuntimeException("Update failed");

        return jpaRepo.findById(userId).get();
    }

    // DELETE
    @Transactional
    public void deleteUser(Long userId) {
        AppUser user = jpaRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        jdbcRepo.deleteUser(userId);
    }
    
    // Additional method to change password
    @Transactional
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        AppUser user = jpaRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Verify old password
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }
        
        // Encrypt and set new password
        String encryptedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encryptedPassword);
        user.setUpdatedAt(LocalDateTime.now());
        
        jpaRepo.save(user);
    }
}