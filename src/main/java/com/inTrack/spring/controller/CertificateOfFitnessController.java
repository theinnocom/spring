package com.inTrack.spring.controller;

import com.inTrack.spring.dto.requestDTO.CertificateOfFitnessReqDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.CertificateOfFitnessService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vijayan
 */

@RestController
public class CertificateOfFitnessController extends BaseController {

    @Autowired
    private CertificateOfFitnessService certificateOfFitnessService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/certificate")
    public ResponseDTO createCertificateOfFitness(@RequestBody final CertificateOfFitnessReqDTO certificateOfFitnessReqDTO) {
        return super.success(responseDTO, this.certificateOfFitnessService.createCertificateOfFitness(certificateOfFitnessReqDTO, null), ApplicationMessageStore.CERTIFICATE_CREATE_SUCCESS);
    }

    @GetMapping(value = "/certificate")
    public ResponseDTO getCertificateOfFitness(@RequestParam(required = false) final Long certificateId) {
        return super.success(responseDTO, this.certificateOfFitnessService.getCertificateOfFitness(certificateId), ApplicationMessageStore.CERTIFICATE_FETCH_SUCCESS);
    }

    @PutMapping(value = "/certificate")
    public ResponseDTO updateCertificateOfFitness(@RequestParam final Long certificateId, @RequestBody final CertificateOfFitnessReqDTO certificateOfFitnessReqDTO) {
        return super.success(responseDTO, this.certificateOfFitnessService.updateCertificateOfFitness(certificateId, certificateOfFitnessReqDTO), ApplicationMessageStore.CERTIFICATE_UPDATE_SUCCESS);
    }

    @DeleteMapping(value = "/certificate")
    public ResponseDTO deleteCertificateOfFitness(@RequestParam(required = false) final Long certificateId) {
        this.certificateOfFitnessService.deleteCertificateOfFitness(certificateId);
        return super.success(responseDTO, null, ApplicationMessageStore.CERTIFICATE_DELETE_SUCCESS);
    }
}
