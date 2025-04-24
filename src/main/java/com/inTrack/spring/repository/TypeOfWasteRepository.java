package com.inTrack.spring.repository;

import com.inTrack.spring.entity.TypeOfWaste;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeOfWasteRepository extends JpaRepository<TypeOfWaste, Long> {
}
