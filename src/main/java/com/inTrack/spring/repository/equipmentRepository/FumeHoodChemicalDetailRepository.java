package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.equipmentEntity.FumeHood;
import com.inTrack.spring.entity.equipmentEntity.FumeHoodChemicalDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FumeHoodChemicalDetailRepository extends JpaRepository<FumeHoodChemicalDetail, Long> {
    List<FumeHoodChemicalDetail> findByFumeHood(FumeHood fumeHood);

    void deleteByFumeHood(FumeHood fumeHood);
}
