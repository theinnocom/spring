package com.inTrack.spring.service;

import com.inTrack.spring.dto.common.FacilityOwnerDetailDTO;
import com.inTrack.spring.entity.FacilityOwnerDetail;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.FacilityOwnerDetailRepository;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
@RequiredArgsConstructor
public class FacilityOwnerDetailService {

    private final FacilityOwnerDetailRepository facilityOwnerDetailRepository;

    public FacilityOwnerDetail createFacilityOwnerDetail(final FacilityOwnerDetailDTO facilityOwnerDetailDTO) {
        final FacilityOwnerDetail facilityOwnerDetail;
        if (facilityOwnerDetailDTO.getId() != null) {
            facilityOwnerDetail = facilityOwnerDetailRepository.findById(facilityOwnerDetailDTO.getId())
                    .orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
        } else {
            facilityOwnerDetail = new FacilityOwnerDetail();
        }
        for (final Field field : FacilityOwnerDetail.class.getDeclaredFields()) {
            field.setAccessible(true);
            if ((facilityOwnerDetailDTO.getId() == null && field.getName().equalsIgnoreCase("id"))) {
                continue;
            }
            try {
                final Object value = this.getFieldValue(facilityOwnerDetailDTO, field.getName());
                if (value != null) {
                    field.set(facilityOwnerDetail, value);
                }
            } catch (final NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                throw new ValidationError("Error setting field: " + field.getName());
            }
        }
        if (facilityOwnerDetail != null) {
            return this.facilityOwnerDetailRepository.save(facilityOwnerDetail);
        } else {
            return null;
        }
    }

    public FacilityOwnerDetail getFacilityOwnerDetail(final Long ownerId) {
        final FacilityOwnerDetail facilityOwnerDetail = facilityOwnerDetailRepository.findById(ownerId)
                .orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
        return facilityOwnerDetail;
    }

    private Object getFieldValue(final FacilityOwnerDetailDTO facilityOwnerDetailDTO, final String fieldName) throws NoSuchFieldException, IllegalAccessException {
        final Field field = FacilityOwnerDetailDTO.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(facilityOwnerDetailDTO);
    }
}
