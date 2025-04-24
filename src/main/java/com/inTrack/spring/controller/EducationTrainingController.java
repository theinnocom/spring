package com.inTrack.spring.controller;

import com.inTrack.spring.dto.common.EducationTrainingDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.EducationTrainingService;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class EducationTrainingController extends BaseController {

    private final EducationTrainingService educationTrainingService;

    @PostMapping(value = "/training")
    public ResponseDTO createTraining(@RequestBody final EducationTrainingDTO educationTrainingDTO) {
        return success(new ResponseDTO(), this.educationTrainingService.createEducationTraining(educationTrainingDTO), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/training")
    public ResponseDTO getAllTraining(@RequestParam final Long facilityId) {
        return success(new ResponseDTO(), this.educationTrainingService.getEducationTrainingList(facilityId), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/training/{id}")
    public ResponseDTO getTraining(@PathVariable final Long id) {
        return success(new ResponseDTO(), this.educationTrainingService.getEducationTraining(id), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/training")
    public ResponseDTO updateTraining(@RequestBody final EducationTrainingDTO educationTrainingDTO) {
        return success(new ResponseDTO(), this.educationTrainingService.updateEducationTraining(educationTrainingDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/training/{id}")
    public ResponseDTO deleteTraining(@PathVariable final Long id) {
        this.educationTrainingService.deleteEducationTraining(id);
        return success(new ResponseDTO(), ApplicationMessageStore.SUCCESS);
    }
}
