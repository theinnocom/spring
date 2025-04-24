package com.inTrack.spring.service.equipmentService.AgencyService;


import com.inTrack.spring.dto.AgencyDTO;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.*;
import com.inTrack.spring.repository.AgencyRepo.GeneratorAgencyRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vijayan M
 */
@Service
@RequiredArgsConstructor
public class AgencyService {

    private final BoilerCogenAgencyRepository boilerCogenAgencyRepository;
    private final BulkOxygenStorageAgencyRepository bulkOxygenStorageAgencyRepository;
    private final CoolingTowerAgencyRepository coolingTowerAgencyRepository;
    private final GeneratorAgencyRepository generatorAgencyRepository;
    private final IncineratorCrematoriesAgencyRepository incineratorCrematoriesAgencyRepository;
    private final ParameterTypeRepository parameterTypeRepository;
    private final StackAgencyRepository stackAgencyRepository;
    private final InspectionTypeRepository inspectionTypeRepository;

    public List<AgencyDTO> getAgencyList(final String equipmentName) {
        return this.boilerCogenAgencyRepository.findByColumnTrue(equipmentName);
    }

    public List<BulkOxygenStorageAgency> getBulkOxygenStorageAgencies() {
        return this.bulkOxygenStorageAgencyRepository.findAll();
    }

    public List<CoolingTowerAgency> getCoolingTowerAgencies() {
        return this.coolingTowerAgencyRepository.findAll();
    }

    public List<GeneratorAgency> getGeneratorAgencies() {
        return this.generatorAgencyRepository.findAll();
    }

    public List<IncineratorCrematoriesAgency> getIncineratorCrematoriesAgencies() {
        return this.incineratorCrematoriesAgencyRepository.findAll();
    }

    public List<ParameterType> getParameterTypes() {
        return this.parameterTypeRepository.findAll();
    }

    public List<StackAgency> getStackAgencies() {
        return this.stackAgencyRepository.findAll();
    }

    public List<InspectionType> getInspectionType() {
        return this.inspectionTypeRepository.findAll();
    }
}
