package com.inTrack.spring.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CertificateOfCorrectionResDTO {

    public Long cocid;
    private String status;
    private Long createdAt;
    private Long updatedAt;
}
