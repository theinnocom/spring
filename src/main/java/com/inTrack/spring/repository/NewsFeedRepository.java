package com.inTrack.spring.repository;

import com.inTrack.spring.entity.Facility;
import com.inTrack.spring.entity.NewsFeed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsFeedRepository extends JpaRepository<NewsFeed, Long> {
    Long countByFacility(Facility facility);

    List<NewsFeed> findByFacility(Facility facility);
}
