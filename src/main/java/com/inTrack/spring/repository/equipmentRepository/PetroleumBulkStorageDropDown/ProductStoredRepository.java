package com.inTrack.spring.repository.equipmentRepository.PetroleumBulkStorageDropDown;

import com.inTrack.spring.entity.equipmentEntity.petroleumDropDown.ProductStored;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductStoredRepository extends JpaRepository<ProductStored, Long> {

    @Query(value = "select * from inTrack.product_stored where (lower(type) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<ProductStored> getProductStored(String search);
}
