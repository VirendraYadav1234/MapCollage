package com.mapCollage.Security.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mapCollage.Security.Entity.AppUser;
import com.mapCollage.Security.Model.UserPatchRequest;
import com.mapCollage.Security.Model.UserRequest;
import com.mapCollage.Security.Service.AppUserService;
@RestController
@RequestMapping("/api/users")
public class AppUserController {

    private final AppUserService service;

    public AppUserController(AppUserService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping("/register")
    public ResponseEntity<AppUser> createUser(@RequestBody UserRequest request) {
        return ResponseEntity.ok(service.createUser(request));
    }

    // GET single
    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(service.getUser(id));
    }
    

    // GET all
    @GetMapping
    public ResponseEntity<List<AppUser>> getAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    // PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<AppUser> patchUser(
            @PathVariable Long id,
            @RequestBody UserPatchRequest request) {
        return ResponseEntity.ok(service.patchUser(id, request));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
