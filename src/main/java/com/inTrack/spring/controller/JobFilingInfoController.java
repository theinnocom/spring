package com.inTrack.spring.controller;

import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.JobFilingInfoService;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class JobFilingInfoController extends BaseController {

    private final JobFilingInfoService jobFilingInfoService;

    @PostMapping(value = "/job-filing")
    public ResponseDTO createJObFilingInfo(@RequestBody final JobFilingInformationReqDTO jobFilingInformationReqDTO) {
        return success(new ResponseDTO(), this.jobFilingInfoService.createJobFilingInformation(jobFilingInformationReqDTO), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/job-filing")
    public ResponseDTO getJobFilingInfo(@RequestParam final Long id) {
        return success(new ResponseDTO(), this.jobFilingInfoService.getJobFilingInformation(id), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/job-filing/building")
    public ResponseDTO getAllJobFilingInfo(@RequestParam final Long id) {
        return success(new ResponseDTO(), this.jobFilingInfoService.getAllJobFilingInformation(id), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/job-filing")
    public ResponseDTO updateJobFiling(@RequestBody final JobFilingInformationReqDTO jobFilingInformationReqDTO) {
        return success(new ResponseDTO(), this.jobFilingInfoService.updateJobFilingInformation(jobFilingInformationReqDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/job-filing")
    public ResponseDTO deleteJobFiling(@RequestParam final Long id) {
        this.jobFilingInfoService.deleteJobFilingInformation(id);
        return success(new ResponseDTO(), ApplicationMessageStore.SUCCESS);
    }
}
