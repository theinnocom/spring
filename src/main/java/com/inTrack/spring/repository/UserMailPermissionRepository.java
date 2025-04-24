package com.inTrack.spring.repository;

import com.inTrack.spring.entity.User;
import com.inTrack.spring.entity.UserMailPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMailPermissionRepository extends JpaRepository<UserMailPermission, Long> {
    UserMailPermission findByUser(User user);
}
