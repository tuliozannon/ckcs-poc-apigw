package com.ckcspoc.ckcspocapigw.service;

import com.ckcspoc.ckcspocapigw.common.dto.CKCSUserDto;
import com.ckcspoc.ckcspocapigw.common.service.CKCSAPIIntegrationService;
import com.ckcspoc.ckcspocapigw.common.service.CKCSAuthenticationService;
import com.ckcspoc.ckcspocapigw.common.dto.CKCSCollaborativeSessionDto;
import com.ckcspoc.ckcspocapigw.dto.DocumentDto;
import com.ckcspoc.ckcspocapigw.dto.PersonShortenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CKCSService {
    private final PersonService personService;
    private final CKCSAuthenticationService ckcsAuthenticationService;
    private final CKCSAPIIntegrationService ckcsAPIIntegrationService;
    private final Boolean ckcsUsesDocStorage;


    public CKCSService(PersonService personService,
                       CKCSAuthenticationService ckcsAuthenticationService,
                       CKCSAPIIntegrationService ckcsAPIIntegrationService,
                       @Value("${ckcs-uses-doc-storage}") Boolean ckcsUsesDocStorage) {
        this.personService = personService;
        this.ckcsAuthenticationService = ckcsAuthenticationService;
        this.ckcsAPIIntegrationService = ckcsAPIIntegrationService;
        this.ckcsUsesDocStorage = ckcsUsesDocStorage;
    }

    // Deliver token based on personId
    public String getToken(Integer personId){
        PersonShortenDto personDto = this.personService.getShortenWithRoles(personId);
        CKCSUserDto userDto = this.fromPersonToCKCSUser(personDto, personId);
        String token = this.ckcsAuthenticationService.getUserToken(userDto);
        return token;
    }
    private CKCSUserDto fromPersonToCKCSUser(PersonShortenDto personDto, Integer personId){
        CKCSUserDto dto = new CKCSUserDto();
        dto.setId(personId);
        dto.setName(personDto.getFirstName()+" "+ personDto.getLastName());
        dto.setEmail(personDto.getEmail());
        dto.setAvatarUrl(personDto.getPhotoThumbnail());
        return dto;
    }

    public boolean hasToInitializeData(String documentId) {
        return (((this.ckcsUsesDocStorage) && (!this.isDocumentAtStorage(documentId)))
                || ((!this.ckcsUsesDocStorage) && (!this.isCollaborationExists(documentId))));
    }

    private Boolean isDocumentAtStorage(String documentId){
        return this.ckcsAPIIntegrationService.isDocumentAtStorage(documentId);
    }

    private Boolean isCollaborationExists(String documentId){
        return this.ckcsAPIIntegrationService.isCollaborationExists(documentId);
    }

    public DocumentDto sendDocumentToCloud(DocumentDto documentDto) {
        if ((documentDto!=null)
                && (documentDto.getChannelId() != null)
                && (documentDto.getContent()!=null)){
            String documentId = documentDto.getChannelId();
            // TODO: get bundle version
            String bundleVersionId = "ckeditor-1.0.3";

            CKCSCollaborativeSessionDto collaborativeSessionDto = new CKCSCollaborativeSessionDto();
            collaborativeSessionDto.setDocument_id(documentDto.getChannelId());
            collaborativeSessionDto.setBundle_version(bundleVersionId);
            collaborativeSessionDto.setData(documentDto.getContent());
            this.ckcsAPIIntegrationService.createCollaboration(collaborativeSessionDto);
        }
        return documentDto;
    }
}
