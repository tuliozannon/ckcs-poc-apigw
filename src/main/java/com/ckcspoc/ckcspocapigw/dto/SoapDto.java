package com.ckcspoc.ckcspocapigw.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.Instant;

@Data
@EqualsAndHashCode(exclude = "source")
public class SoapDto {

    private Integer id;

    private Integer appointmentId;

    private Integer patientId;

    private Integer businessId;

    private String medicalNotes;

    private String dischargeNotes;

    private Instant modificationDate;

    private Instant creationDate;
}
