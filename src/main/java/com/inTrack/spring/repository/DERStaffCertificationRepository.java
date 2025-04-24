package com.inTrack.spring.repository;

import com.inTrack.spring.entity.DERStaffCertification;
import com.inTrack.spring.entity.Spcc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DERStaffCertificationRepository extends JpaRepository<DERStaffCertification, Long> {
    List<DERStaffCertification> findBySpcc(Spcc spcc);
}
