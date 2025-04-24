package com.inTrack.spring.repository;

import com.inTrack.spring.entity.CertificateType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateTypeRepository extends JpaRepository<CertificateType, Long> {
}
