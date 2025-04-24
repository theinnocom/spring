package com.inTrack.spring.repository;

import com.inTrack.spring.entity.FacilityAgency.PostLogAgency;
import com.inTrack.spring.entity.PostLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLogAgencyRepository extends JpaRepository<PostLogAgency, Long> {
    PostLogAgency findByPostLog(PostLog postLog);
}
