package com.inTrack.spring.repository;

import com.inTrack.spring.entity.User;
import com.inTrack.spring.entity.UserFacilityRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFacilityRelationRepository extends JpaRepository<UserFacilityRelation, Long> {
    List<UserFacilityRelation> findByUser(User user);
}
