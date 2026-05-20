package com.sparta.todo.services;

import com.sparta.todo.entities.Spartan;
import com.sparta.todo.repositories.SpartanRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SpartanService implements UserDetailsService {

    private final SpartanRepository repository;

    public SpartanService(SpartanRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Spartan spartan = repository.findbyUserName(username);

        // ADD THIS NULL CHECK
        if (spartan == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return User.builder()
                .username(spartan.getUsername())
                .password(spartan.getPassword()) // should already be encoded
                .authorities("ROLE_" + spartan.getRole()) // ADD AUTHORITIES
                .build();
    }

}
