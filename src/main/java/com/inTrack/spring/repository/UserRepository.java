package com.inTrack.spring.repository;


import com.inTrack.spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author vijayan
 */

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String upperCase);

    User findByEmailOrUserName(String email, String userName);

    List<User> findByIsProfileActive(boolean active);

    User findByUserId(Long id);
}
