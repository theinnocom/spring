package com.inTrack.spring.repository;

import com.inTrack.spring.entity.EnquiryForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author vijayan
 */

public interface EnquiryFormRepository extends JpaRepository<EnquiryForm, Long> {
    List<EnquiryForm> findByIsProfileActive(boolean profileActive);

    EnquiryForm findByEnquiryId(Long id);
}
