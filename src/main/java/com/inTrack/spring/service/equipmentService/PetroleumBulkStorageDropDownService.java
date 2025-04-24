package com.inTrack.spring.service.equipmentService;

import com.inTrack.spring.entity.equipmentEntity.petroleumDropDown.*;
import com.inTrack.spring.repository.equipmentRepository.PetroleumBulkStorageDropDown.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetroleumBulkStorageDropDownService {

    @Autowired
    private DispenserRepository dispenserRepository;

    @Autowired
    private ExternalProtectionRepository externalProtectionRepository;

    @Autowired
    private InternalProtectionRepository internalProtectionRepository;

    @Autowired
    private OverFillProtectionRepository overFillProtectionRepository;

    @Autowired
    private PipeLeakDetectionRepository pipeLeakDetectionRepository;

    @Autowired
    private PipingLocationRepository pipingLocationRepository;

    @Autowired
    private PipingSecondaryContainmentRepository pipingSecondaryContainmentRepository;

    @Autowired
    private PipingTypeRepository pipingTypeRepository;

    @Autowired
    private ProductStoredRepository productStoredRepository;

    @Autowired
    private SpillPreventionRepository spillPreventionRepository;

    @Autowired
    private SubpartRepository subpartRepository;

    @Autowired
    private TankLeakDetectionRepository tankLeakDetectionRepository;

    @Autowired
    private TankLocationRepository tankLocationRepository;

    @Autowired
    private TankSecondaryContainmentRepository tankSecondaryContainmentRepository;

    @Autowired
    private TankStatusRepository tankStatusRepository;

    @Autowired
    private TankTypeRepository tankTypeRepository;

    public List<Dispenser> getDispenser(final String search) {
        return this.dispenserRepository.getDispenser(search);
    }

    public List<ExternalProtection> getExternalProtection(final String search) {
        return this.externalProtectionRepository.getExternalProtection(search);
    }

    public List<InternalProtection> getInternalProtection(final String search) {
        return this.internalProtectionRepository.getInternalProtection(search);
    }

    public List<OverFillProtection> getOverFillProtection(final String search) {
        return this.overFillProtectionRepository.getOverFillProtection(search);
    }

    public List<PipeLeakDetection> getPipeLeakDetection(final String search) {
        return this.pipeLeakDetectionRepository.getPipeLeakDetection(search);
    }

    public List<PipingLocation> getPipingLocation(final String search) {
        return this.pipingLocationRepository.getPipingLocation(search);
    }

    public List<PipingSecondaryContainment> getPipingSecondaryContainment(final String search) {
        return this.pipingSecondaryContainmentRepository.getPipingSecondaryContainment(search);
    }

    public List<PipingType> getPipingType(final String search) {
        return this.pipingTypeRepository.getPipingType(search);
    }

    public List<ProductStored> getProductStored(final String search) {
        return this.productStoredRepository.getProductStored(search);
    }

    public List<SpillPrevention> getSpillPrevention(final String search) {
        return this.spillPreventionRepository.getSpillPrevention(search);
    }

    public List<Subpart> getSubpart(final String search) {
        return this.subpartRepository.getSubpart(search);
    }

    public List<TankLeakDetection> getTankLeakDetection(final String search) {
        return this.tankLeakDetectionRepository.getTankLeakDetection(search);
    }

    public List<TankLocation> getTankLocation(final String search) {
        return this.tankLocationRepository.getTankLocation(search);
    }

    public List<TankSecondaryContainment> getTankSecondaryContainment(final String search) {
        return this.tankSecondaryContainmentRepository.getTankSecondaryContainment(search);
    }

    public List<TankStatus> getTankStatus(final String search) {
        return this.tankStatusRepository.getTankStatus(search);
    }

    public List<TankType> getTankType(final String search) {
        return this.tankTypeRepository.getTankType(search);
    }
}
