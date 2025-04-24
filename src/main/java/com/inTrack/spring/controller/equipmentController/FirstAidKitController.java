package com.inTrack.spring.controller.equipmentController;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.FirstAidKitDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.equipmentService.FirstAidKitService;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FirstAidKitController extends BaseController {

    private final FirstAidKitService firstAidKitService;

    @PostMapping(value = "/first-aid-kit")
    public ResponseDTO createFirstAidKit(@RequestBody final FirstAidKitDTO firstAidKitDTO) {
        return success(new ResponseDTO(), this.firstAidKitService.saveFirstAidKit(firstAidKitDTO), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/first-aid-kit/building/{buildingId}")
    public ResponseDTO getAllFirstAidKit(@PathVariable final Long buildingId) {
        return success(new ResponseDTO(), this.firstAidKitService.getAllFirstAidKit(buildingId), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/first-aid-kit/{id}")
    public ResponseDTO getFirstAidKit(@PathVariable final Long id) {
        return success(new ResponseDTO(), this.firstAidKitService.getFirstAidKit(id), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/first-aid-kit")
    public ResponseDTO getFirstAidKit(@RequestBody final FirstAidKitDTO firstAidKitDTO) {
        return success(new ResponseDTO(), this.firstAidKitService.updateFirstAidKitDTO(firstAidKitDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/first-aid-kit/{id}")
    public ResponseDTO deleteFirstAidKit(@PathVariable final Long id) {
        this.firstAidKitService.deleteFirstAidKit(id);
        return success(new ResponseDTO(), ApplicationMessageStore.SUCCESS);
    }
}
