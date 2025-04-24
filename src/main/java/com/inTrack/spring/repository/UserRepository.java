package com.inTrack.spring.repository;

import com.inTrack.spring.entity.User;
import com.inTrack.spring.store.NativeQuires;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author vijayan
 */

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String upperCase);


    @Query(value = NativeQuires.GET_ALL_ACTIVE_USER, nativeQuery = true)
    List<Object[]> findByIsProfileActive(boolean active);

    @Query(value = NativeQuires.GET_USER, nativeQuery = true)
    List<Object[]> getUser(Long userId);

    User findByUserId(Long id);

    User findByEmailOrLoginId(String email, String loginId);
}
