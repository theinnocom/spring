package com.inTrack.spring.repository;

import com.inTrack.spring.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author vijayan
 */

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
