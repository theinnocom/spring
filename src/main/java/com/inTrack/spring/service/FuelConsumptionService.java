package com.inTrack.spring.service;

import com.inTrack.spring.dto.requestDTO.FuelConsumptionReqDTO;
import com.inTrack.spring.dto.responseDTO.FuelConsumptionMonthResDTO;
import com.inTrack.spring.dto.responseDTO.FuelConsumptionResDTO;
import com.inTrack.spring.entity.Facility;
import com.inTrack.spring.entity.FuelConsumption;
import com.inTrack.spring.entity.FuelType;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.FacilityRepository;
import com.inTrack.spring.repository.FuelConsumptionRepository;
import com.inTrack.spring.repository.FuelTypeRepository;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author vijayan
 */

@Service
public class FuelConsumptionService {

    @Autowired
    private FuelConsumptionRepository fuelconsumptionRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private FuelTypeRepository fuelTypeRepository;

    public FuelConsumptionResDTO createFuelConsumption(final FuelConsumptionReqDTO fuelConsumptionReqDTO, final Long id) {
        final FuelConsumption fuelConsumption;
        if (id == null) {
            final FuelConsumption fuel = this.fuelconsumptionRepository.findByYearAndFuelTypeFuelName(fuelConsumptionReqDTO.getYear(), fuelConsumptionReqDTO.getFuelType().getFuelName());
            if (fuel != null) {
                throw new ValidationError(ApplicationMessageStore.placeHolderMessage(ApplicationMessageStore.FUEL_CONSUMPTION_ALREADY_EXISTS, fuelConsumptionReqDTO.getFuelType().getFuelName(), String.valueOf(fuelConsumptionReqDTO.getYear())));
            }
            fuelConsumption = new FuelConsumption();
            fuelConsumption.setCreatedAt(System.currentTimeMillis());
        } else {
            final Optional<FuelConsumption> fuelInformationOptional = this.fuelconsumptionRepository.findById(id);
            if (fuelInformationOptional.isEmpty()) {
                throw new ValidationError(ApplicationMessageStore.FUEL_CONSUMPTION_NOT_FOUND);
            }
            fuelConsumption = fuelInformationOptional.get();
            fuelConsumption.setUpdatedAt(System.currentTimeMillis());
        }
        fuelConsumption.setFuelType(fuelConsumptionReqDTO.getFuelType());
        fuelConsumption.setYear(fuelConsumptionReqDTO.getYear());
        fuelConsumption.setJanuary(fuelConsumptionReqDTO.getJanuary());
        fuelConsumption.setFebruary(fuelConsumptionReqDTO.getFebruary());
        fuelConsumption.setMarch(fuelConsumptionReqDTO.getMarch());
        fuelConsumption.setApril(fuelConsumptionReqDTO.getApril());
        fuelConsumption.setMay(fuelConsumptionReqDTO.getMay());
        fuelConsumption.setJune(fuelConsumptionReqDTO.getJune());
        fuelConsumption.setJuly(fuelConsumptionReqDTO.getJuly());
        fuelConsumption.setAugust(fuelConsumptionReqDTO.getAugust());
        fuelConsumption.setSeptember(fuelConsumptionReqDTO.getSeptember());
        fuelConsumption.setOctober(fuelConsumptionReqDTO.getOctober());
        fuelConsumption.setNovember(fuelConsumptionReqDTO.getNovember());
        fuelConsumption.setDecember(fuelConsumptionReqDTO.getDecember());
        fuelConsumption.setAnnualFuelConsumption(this.calculateAnnualFuelConsumption(fuelConsumption));
        final Facility facility = this.facilityRepository.findByFacilityIdAndActive(fuelConsumptionReqDTO.getFacilityId(), true);
        if (facility == null) {
            throw new ValidationError(ApplicationMessageStore.FACILITY_NOT_FOUND);
        }
        fuelConsumption.setFacility(facility);
        return this.setFuelConsumption(this.fuelconsumptionRepository.save(fuelConsumption));
    }

    public List<FuelConsumptionResDTO> getFuelConsumption(final Long id, final Long facilityId, final Long year) {
        final List<FuelConsumption> fuelConsumptionList = new LinkedList<>();
        if (id != null) {
            final Optional<FuelConsumption> fuelInformationOptional = this.fuelconsumptionRepository.findById(id);
            if (fuelInformationOptional.isEmpty()) {
                throw new ValidationError(ApplicationMessageStore.FUEL_CONSUMPTION_NOT_FOUND);
            }
            fuelConsumptionList.add(fuelInformationOptional.get());
        } else if (year == null) {
            final Facility facility = this.facilityRepository.findByFacilityIdAndActive(facilityId, true);
            if (facility == null) {
                throw new ValidationError(ApplicationMessageStore.FACILITY_NOT_FOUND);
            }
            fuelConsumptionList.addAll(this.fuelconsumptionRepository.findAllByFacility(facility));
        } else {
            fuelConsumptionList.addAll(this.fuelconsumptionRepository.findByYear(year));
        }
        return fuelConsumptionList
                .stream().map(this::setFuelConsumption).toList();
    }

    public FuelConsumptionResDTO updateFuelConsumption(final FuelConsumptionReqDTO fuelConsumptionReqDTO, final Long id) {
        return this.createFuelConsumption(fuelConsumptionReqDTO, id);
    }

    public void deleteFuelConsumption(final Long id) {
        if (id == null) {
            throw new ValidationError(ApplicationMessageStore.FUEL_CONSUMPTION_NOT_FOUND);
        }
        this.fuelconsumptionRepository.deleteById(id);
    }

    public List<FuelType> getFuelType() {
        return this.fuelTypeRepository.findAll();
    }

    private FuelConsumptionResDTO setFuelConsumption(final FuelConsumption fuelConsumption) {
        final FuelConsumptionResDTO fuelConsumptionResDTO = new FuelConsumptionResDTO();
        fuelConsumptionResDTO.setFuelType(fuelConsumption.getFuelType());
        fuelConsumptionResDTO.setYear(fuelConsumption.getYear());
        final FuelConsumptionMonthResDTO fuelConsumptionMonthResDTO = new FuelConsumptionMonthResDTO();
        fuelConsumptionMonthResDTO.setJanuary(fuelConsumption.getJanuary());
        fuelConsumptionMonthResDTO.setFebruary(fuelConsumption.getFebruary());
        fuelConsumptionMonthResDTO.setMarch(fuelConsumption.getMarch());
        fuelConsumptionMonthResDTO.setApril(fuelConsumption.getApril());
        fuelConsumptionMonthResDTO.setMay(fuelConsumption.getMay());
        fuelConsumptionMonthResDTO.setJune(fuelConsumption.getJune());
        fuelConsumptionMonthResDTO.setJuly(fuelConsumption.getJuly());
        fuelConsumptionMonthResDTO.setAugust(fuelConsumption.getAugust());
        fuelConsumptionMonthResDTO.setSeptember(fuelConsumption.getSeptember());
        fuelConsumptionMonthResDTO.setOctober(fuelConsumption.getOctober());
        fuelConsumptionMonthResDTO.setNovember(fuelConsumption.getNovember());
        fuelConsumptionMonthResDTO.setDecember(fuelConsumption.getDecember());
        fuelConsumptionResDTO.setFuelConsumptionMonthResDTO(fuelConsumptionMonthResDTO);
        fuelConsumptionResDTO.setFacilityId(fuelConsumption.getFacility().getFacilityId());
        fuelConsumptionResDTO.setAnnualFuelConsumption(fuelConsumption.getAnnualFuelConsumption());
        fuelConsumptionResDTO.setId(fuelConsumption.getId());
        return fuelConsumptionResDTO;
    }

    private Double calculateAnnualFuelConsumption(final FuelConsumption fuelConsumption) {
        return fuelConsumption.getJanuary() + fuelConsumption.getFebruary() + fuelConsumption.getMarch() + fuelConsumption.getApril() + fuelConsumption.getMay() +
                fuelConsumption.getJune() + fuelConsumption.getJuly() + fuelConsumption.getAugust() + fuelConsumption.getSeptember() + fuelConsumption.getOctober() + fuelConsumption.getNovember() +
                fuelConsumption.getDecember();
    }

}
