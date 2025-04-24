package com.inTrack.spring.repository;

import com.inTrack.spring.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
