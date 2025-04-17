package com.inTrack.spring.security;

import com.inTrack.spring.config.Properties;
import com.inTrack.spring.entity.User;
import com.inTrack.spring.exception.SecurityError;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.UserRepository;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vijayan
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Properties properties;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {
        if (username == null) {
            throw new SecurityError("Invalid user name");
        }
        final User user = this.userRepository.findByEmailOrUserName(username.toUpperCase(), username.toUpperCase());
        if (user == null) {
            throw new SecurityError("Username does not exist");
        }
        if (!user.getIsProfileActive()) {
            throw new ValidationError(ApplicationMessageStore.USER_NOT_VERIFIED);
        }
        final GrantedAuthority[] auths = {new SimpleGrantedAuthority(user.getRole().getRole())};
        this.userRepository.save(user);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>(List.of(auths)));
    }
}
