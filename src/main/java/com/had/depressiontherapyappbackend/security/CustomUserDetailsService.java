package com.had.depressiontherapyappbackend.security;
 
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.had.depressiontherapyappbackend.entities.User;
import com.had.depressiontherapyappbackend.repositories.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User requiredUser = null;

        try {
            List<User> requiredUserList = this.userRepo.findByEmail(userEmail);
            requiredUser = requiredUserList.get(0);
        } catch(UsernameNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        return requiredUser;
    }

    
}
