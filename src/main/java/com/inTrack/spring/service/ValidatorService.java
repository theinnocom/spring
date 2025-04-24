package com.inTrack.spring.service;

import com.inTrack.spring.entity.User;
import com.inTrack.spring.exception.ResourceNotFoundError;
import com.inTrack.spring.repository.UserRepository;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author vijayan
 */

@Service
public class ValidatorService {

    @Autowired
    private UserRepository userRepository;

    public String getUserIdAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            return auth.getName();
        }
        return "";
    }

    public User validateUser() {
        return this.validateUserByEmail(this.getUserIdAuth());
    }

    public User validateUserByEmail(final String email) {
        if (email == null || email.isEmpty()) {
            throw new ResourceNotFoundError(ApplicationMessageStore.USER_NOT_FOUND);
        }
        final User user = this.userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundError(ApplicationMessageStore.USER_NOT_FOUND);
        }
        return user;
    }

    public User validateUser(final Long id) {
        final User user = this.userRepository.findByUserId(id);
        if (user == null) {
            throw new ResourceNotFoundError(ApplicationMessageStore.USER_NOT_FOUND);
        }
        return user;
    }
}
