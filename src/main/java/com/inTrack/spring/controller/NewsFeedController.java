package com.inTrack.spring.controller;

import com.inTrack.spring.dto.common.NewsFeedDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.NewsFeedService;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class NewsFeedController extends BaseController {

    private final NewsFeedService newsFeedService;

    @PostMapping(value = "/news-feed")
    public ResponseDTO createNewsFeed(@RequestBody final NewsFeedDTO newsFeedDTO) {
        return success(new ResponseDTO(), this.newsFeedService.createNewsFeed(newsFeedDTO), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/news-feed")
    public ResponseDTO getNewsFeed(@RequestParam final Long id) {
        return success(new ResponseDTO(), this.newsFeedService.getNewsFeed(id), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/news-feed/facility")
    public ResponseDTO getAllNewsFeed(@RequestParam final Long facilityId) {
        return success(new ResponseDTO(), this.newsFeedService.getAllNewsFeeds(facilityId), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/news-feed")
    public ResponseDTO updateNewsFeed(@RequestBody final NewsFeedDTO newsFeedDTO) {
        return success(new ResponseDTO(), this.newsFeedService.updateNewsFeed(newsFeedDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/news-feed")
    public ResponseDTO deleteNewsFeed(@RequestParam final Long id) {
        this.newsFeedService.deleteNewsFeed(id);
        return success(new ResponseDTO(), ApplicationMessageStore.SUCCESS);
    }
}
