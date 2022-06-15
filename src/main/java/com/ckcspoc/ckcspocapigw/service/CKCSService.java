package com.ckcspoc.ckcspocapigw.service;

import com.ckcspoc.ckcspocapigw.common.dto.CKCSUserDto;
import com.ckcspoc.ckcspocapigw.common.service.CKCSAuthenticationService;
import com.ckcspoc.ckcspocapigw.dto.PersonShortenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CKCSService {
    private final PersonService personService;
    private final CKCSAuthenticationService ckcsAuthenticationService;
    private final CKCSEventService ckcsEventService;


    public CKCSService(PersonService personService,
                       CKCSAuthenticationService ckcsAuthenticationService,
                       CKCSEventService ckcsEventService) {
        this.personService = personService;
        this.ckcsAuthenticationService = ckcsAuthenticationService;
        this.ckcsEventService = ckcsEventService;
    }

    // Delegate to ckcsEventService to treat Webhook Events
    public void handleEvent(String event){
        this.ckcsEventService.handleEvent(event);
    }

    // Deliver token based on personId
    public String getToken(Integer personId){
        PersonShortenDto personDto = this.personService.getShortenWithRoles(personId);
        CKCSUserDto userDto = this.fromPersonToCKCSUser(personDto, personId);
        return this.ckcsAuthenticationService.getUserToken(userDto);
    }
    private CKCSUserDto fromPersonToCKCSUser(PersonShortenDto personDto, Integer personId){
        CKCSUserDto dto = new CKCSUserDto();
        dto.setId(personId);
        dto.setName(personDto.getFirstName()+" "+ personDto.getLastName());
        dto.setEmail(personDto.getEmail());
        dto.setAvatarUrl(personDto.getPhotoThumbnail());
        return dto;
    }

    // TODO: be adapted
    public String getDocumentId(String baseId, Integer type) throws Exception{
        String documentId = "t"+type+"_"+baseId;
        return documentId;
    }





}
