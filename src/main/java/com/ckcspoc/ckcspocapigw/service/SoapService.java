package com.ckcspoc.ckcspocapigw.service;

import com.ckcspoc.ckcspocapigw.converter.SoapConverter;
import com.ckcspoc.ckcspocapigw.dao.SoapDao;
import com.ckcspoc.ckcspocapigw.dto.DocumentDto;
import com.ckcspoc.ckcspocapigw.dto.SoapDto;
import com.ckcspoc.ckcspocapigw.model.DbSoap;
import com.ckcspoc.ckcspocapigw.util.DocumentUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@Service
@Slf4j
public class SoapService {

    private final SoapDao soapDao;

    private final SoapConverter soapConverter;

    private final CKCSService ckcsService;

    public SoapService(SoapDao soapDao,
                       SoapConverter soapConverter,
                       CKCSService ckcsService) {
        this.soapDao = soapDao;
        this.soapConverter = soapConverter;
        this.ckcsService = ckcsService;
    }

    // TODO: be adapted
    public String getChannelId(Integer baseId, Integer type) throws Exception{
        String documentId = DocumentUtil.getDocumentId(baseId, type);
        if (this.ckcsService.hasToInitializeData(documentId)){
            DocumentDto dto = this.getDocument(documentId);
            this.ckcsService.sendDocumentToCloud(dto);
        }
        return documentId;
    }

    public DocumentDto getDocument(String documentId){
        DocumentDto documentDto = null;
        Integer soapId = DocumentUtil.getSoapIdFromDocumentId(documentId);
        SoapDto soap = this.findById(soapId);
        if (soap != null){
            documentDto = new DocumentDto();
            documentDto.setId(soap.getId());
            documentDto.setChannelId(documentId);
            if (DocumentUtil.isMedicalNote(documentId)){
                documentDto.setContent(soap.getMedicalNotes());
            }
            else if (DocumentUtil.isDischargedNote(documentId)){
                documentDto.setContent(soap.getDischargeNotes());
            }
        }

        return documentDto;
    }

    public DocumentDto updateDocument(DocumentDto document){
        SoapDto soapDto = this.updateNote(document.getChannelId(), document.getContent());
        return this.getDocument(document.getChannelId());
    }

    private SoapDto updateNote(String documentId, String content){
        Integer soapId = DocumentUtil.getSoapIdFromDocumentId(documentId);
        SoapDto soapDto = this.findById(soapId);
        if (soapDto != null){
            if (DocumentUtil.isMedicalNote(documentId)){
                soapDto.setMedicalNotes(content);
            }
            else if (DocumentUtil.isDischargedNote(documentId)){
                soapDto.setDischargeNotes(content);
            }
            this.update(soapId, soapDto);
        }
        return soapDto;
    }

    private SoapDto findById(Integer id) {
        SoapDto soapDto = null;
        try {
            soapDto = this.soapDao.findById(id)
                    .map(this.soapConverter::fromDbToDto)
                    .orElseThrow(() -> new Exception("Soap with id=" + id + " does not exist."));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return soapDto;
    }

    @Transactional
    protected SoapDto update(Integer id, SoapDto soap) {
        return Optional.of(this.soapDao.findById(id))
                .map(dbSoap -> this.soapConverter.partialConvertFromDtoToDb(soap, dbSoap.get()))
                .map(this::saveAndRefresh)
                .map(this.soapConverter::fromDbToDto)
                .get();
    }

    protected DbSoap saveAndRefresh(DbSoap dbSoap) {
        return this.soapDao.save(dbSoap);
    }
}
