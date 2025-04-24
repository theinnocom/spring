package com.inTrack.spring.controller;

import com.inTrack.spring.dto.PostLogDTO;
import com.inTrack.spring.dto.requestDTO.CommonListReqDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.PostLogService;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostLogController extends BaseController {

    private final PostLogService postLogService;
    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/post-log")
    public ResponseDTO createPostLog(@RequestBody final PostLogDTO postLogDTO) {
        return success(responseDTO, this.postLogService.createPostLog(null, postLogDTO), ApplicationMessageStore.POST_LOG_CREATE_SUCCESS);
    }

    @GetMapping(value = "/post-log/{id}")
    public ResponseDTO getPostLog(@PathVariable final Long id) {
        return success(responseDTO, this.postLogService.getPostLogById(id), ApplicationMessageStore.POST_LOG_FETCH_SUCCESS);
    }

    @GetMapping(value = "/post-log/facility/{id}")
    public ResponseDTO getPostLogByFacility(@PathVariable final Long id, final CommonListReqDTO commonListReqDTO) {
        return success(responseDTO, this.postLogService.getPostLogByFacility(id, commonListReqDTO), ApplicationMessageStore.POST_LOG_FETCH_SUCCESS);
    }

    @PutMapping(value = "/post-log")
    public ResponseDTO updatePostLog(@RequestBody final PostLogDTO postLogDTO) {
        return success(responseDTO, this.postLogService.updatePostLog(postLogDTO), ApplicationMessageStore.POST_LOG_UPDATE_SUCCESS);
    }

    @DeleteMapping(value = "/post-log")
    public ResponseDTO deletePostLog(@RequestParam final Long id) {
        this.postLogService.deletePostLog(id);
        return success(responseDTO, ApplicationMessageStore.POST_LOG_DELETE_SUCCESS);
    }
}
