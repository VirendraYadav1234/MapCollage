package com.mapCollage.Security.Repositry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.mapCollage.Security.Entity.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
	AppUser findByUsername(String username);

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

}

