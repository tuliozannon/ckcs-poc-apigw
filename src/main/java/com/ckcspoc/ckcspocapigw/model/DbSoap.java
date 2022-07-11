package com.ckcspoc.ckcspocapigw.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "soap")
@Data
public class DbSoap {

    @Id
    @SequenceGenerator(name = "soap_id_seq",
            sequenceName = "soap_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "soap_id_seq")
    @Column(updatable = false)
    private Integer id;

    @Column(updatable = false)
    private Integer appointmentId;

    @Column(updatable = false)
    private Integer patientId;

    @Column(updatable = false)
    private Integer businessId;

    private String medicalNotes;

    private String dischargeNotes;

    private boolean deleted;

    @CreatedDate
    @Column(updatable = false)
    private Instant creationDate;

    @LastModifiedDate
    private Instant modificationDate;
}
