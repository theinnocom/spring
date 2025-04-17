package com.inTrack.spring.repository;

import com.inTrack.spring.entity.User;
import com.inTrack.spring.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author vijayan
 */

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findByUser(User user);
}
