package com.mapCollage.Security.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.mapCollage.Security.Entity.AppUser;
import com.mapCollage.Security.Model.UserPrincipal;
import com.mapCollage.Security.Repositry.AppUserRepository;

@Service
public class MyUserDetailService implements UserDetailsService {

    private final AppUserRepository jpaRepo;

    public MyUserDetailService(AppUserRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = jpaRepo.findByUsername(username);
        if (appUser == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return new UserPrincipal(appUser);
    }
}