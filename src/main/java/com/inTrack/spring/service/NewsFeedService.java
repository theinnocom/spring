package com.inTrack.spring.service;

import com.inTrack.spring.dto.common.NewsFeedDTO;
import com.inTrack.spring.entity.Facility;
import com.inTrack.spring.entity.NewsFeed;
import com.inTrack.spring.entity.SystemConfig;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.FacilityRepository;
import com.inTrack.spring.repository.NewsFeedRepository;
import com.inTrack.spring.repository.SystemConfigRepository;
import com.inTrack.spring.store.ApplicationMessageStore;
import com.inTrack.spring.store.ConstantStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsFeedService {

    private final NewsFeedRepository newsFeedRepository;
    private final FacilityRepository facilityRepository;
    private final SystemConfigRepository systemConfigRepository;

    public NewsFeedDTO createNewsFeed(final NewsFeedDTO newsFeedDTO) {
        NewsFeed newsFeed;
        if (!ObjectUtils.isEmpty(newsFeedDTO.getId())) {
            newsFeed = this.newsFeedRepository.findById(newsFeedDTO.getId())
                    .orElseThrow(() -> new RuntimeException(ApplicationMessageStore.DATA_NOT_FOUND));
        } else {
            if (ObjectUtils.isEmpty(newsFeedDTO.getFacilityId())) {
                throw new ValidationError(ApplicationMessageStore.FACILITY_ID_MANDATORY);
            }
            final Facility facility = this.facilityRepository.findByFacilityId(newsFeedDTO.getFacilityId());
            if (facility == null) {
                throw new ValidationError(ApplicationMessageStore.FACILITY_NOT_FOUND);
            }
            final Long newsFeedCount = this.newsFeedRepository.countByFacility(facility);
            final SystemConfig systemConfig = this.systemConfigRepository.findByKey(ConstantStore.NEWS_FEED);
            if (newsFeedCount >= Long.parseLong(systemConfig.getValue())) {
                throw new ValidationError(ApplicationMessageStore.MAX_NEWS_FEED_EXISTS);
            }
            newsFeed = new NewsFeed();
            newsFeed.setFacility(facility);
        }
        newsFeed.setNewsFeed(newsFeedDTO.getNewsFeed());

        return this.setNewsFeedDTO(this.newsFeedRepository.save(newsFeed));
    }

    public NewsFeedDTO getNewsFeed(final Long id) {
        final NewsFeed newsFeed = this.newsFeedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ApplicationMessageStore.DATA_NOT_FOUND));
        return this.setNewsFeedDTO(newsFeed);
    }

    public List<NewsFeedDTO> getAllNewsFeeds(final Long facilityId) {
        final Facility facility = this.facilityRepository.findByFacilityId(facilityId);
        if (facility == null) {
            throw new ValidationError(ApplicationMessageStore.FACILITY_NOT_FOUND);
        }
        final List<NewsFeed> newsFeeds = this.newsFeedRepository.findByFacility(facility);
        return newsFeeds.stream().map(this::setNewsFeedDTO).collect(Collectors.toList());
    }

    public NewsFeedDTO updateNewsFeed(final NewsFeedDTO newsFeedDTO) {
        if (ObjectUtils.isEmpty(newsFeedDTO.getId())) {
            throw new ValidationError(ApplicationMessageStore.ID_MANDATORY);
        }
        return this.createNewsFeed(newsFeedDTO);
    }

    public void deleteNewsFeed(final Long id) {
        this.newsFeedRepository.deleteById(id);
    }

    private NewsFeedDTO setNewsFeedDTO(final NewsFeed newsFeed) {
        final NewsFeedDTO newsFeedDTO = new NewsFeedDTO();
        newsFeedDTO.setId(newsFeed.getId());
        newsFeedDTO.setNewsFeed(newsFeed.getNewsFeed());
        newsFeedDTO.setFacilityId(newsFeed.getFacility().getFacilityId());
        return newsFeedDTO;
    }
}
