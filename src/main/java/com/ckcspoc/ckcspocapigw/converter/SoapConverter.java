package com.ckcspoc.ckcspocapigw.converter;

import com.ckcspoc.ckcspocapigw.dto.SoapDto;
import com.ckcspoc.ckcspocapigw.model.DbSoap;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


@Component
public class SoapConverter {

    public SoapDto fromDbToDto(DbSoap dbSoap) {
        Assert.notNull(dbSoap, "The given dbSoap must not be null");
        SoapDto soap = new SoapDto();

        soap.setId(dbSoap.getId());
        soap.setAppointmentId(dbSoap.getAppointmentId());
        soap.setBusinessId(dbSoap.getBusinessId());
        soap.setPatientId(dbSoap.getPatientId());
        soap.setMedicalNotes(dbSoap.getMedicalNotes());
        soap.setDischargeNotes(dbSoap.getDischargeNotes());
        soap.setModificationDate(dbSoap.getModificationDate());
        soap.setCreationDate(dbSoap.getCreationDate());

        return soap;
    }

    /*
    public void fromDtoToDb(SoapDto soap, DbSoap dbSoap) {
        Assert.notNull(soap, "The given soap must not be null");
        dbSoap.setAppointmentId(soap.getAppointmentId());
        dbSoap.setBusinessId(soap.getBusinessId());
        dbSoap.setPatientId(soap.getPatientId());
        dbSoap.setMedicalNotes(soap.getMedicalNotes());
        dbSoap.setDischargeNotes(soap.getDischargeNotes());
        dbSoap.setModificationDate(soap.getModificationDate());
        dbSoap.setCreationDate(soap.getCreationDate());
    }
    */

    public DbSoap partialConvertFromDtoToDb(SoapDto dto, DbSoap db) {
        db.setDischargeNotes(dto.getDischargeNotes());
        db.setMedicalNotes(dto.getMedicalNotes());
        return db;
    }

}
