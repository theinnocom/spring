package com.inTrack.spring.repository;

import com.inTrack.spring.entity.CertificateOfFitness;
import com.inTrack.spring.entity.Facility;
import com.inTrack.spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CertificateOfFitnessRepository extends JpaRepository<CertificateOfFitness, Long> {

    CertificateOfFitness findByCertificateIdAndIsActive(Long certificateId, Boolean activeStatus);

    @Query(value = "select * from inTrack.certificate_of_fitness where certificate_number in ?1", nativeQuery = true)
    List<String> findByCertificateNumberIn(List<String> certificateIds);
}
