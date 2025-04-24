package com.inTrack.spring.repository;

import com.inTrack.spring.entity.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemConfigRepository extends JpaRepository<SystemConfig, Long> {
    SystemConfig findByKey(String newsFeed);
}
