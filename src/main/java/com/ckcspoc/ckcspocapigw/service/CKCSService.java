package com.ckcspoc.ckcspocapigw.service;

import com.ckcspoc.ckcspocapigw.common.dto.CKCSUserDto;
import com.ckcspoc.ckcspocapigw.common.service.CKCSAPIIntegrationService;
import com.ckcspoc.ckcspocapigw.common.service.CKCSAuthenticationService;
import com.ckcspoc.ckcspocapigw.dto.DocumentDto;
import com.ckcspoc.ckcspocapigw.dto.PersonShortenDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
public class CKCSService {
    private final PersonService personService;
    private final CKCSAuthenticationService ckcsAuthenticationService;
    private final CKCSEventService ckcsEventService;
    private final CKCSAPIIntegrationService ckcsAPIIntegrationService;


    public CKCSService(PersonService personService,
                       CKCSAuthenticationService ckcsAuthenticationService,
                       CKCSEventService ckcsEventService,
                       CKCSAPIIntegrationService ckcsAPIIntegrationService) {
        this.personService = personService;
        this.ckcsAuthenticationService = ckcsAuthenticationService;
        this.ckcsEventService = ckcsEventService;
        this.ckcsAPIIntegrationService = ckcsAPIIntegrationService;
    }

    // Delegate to ckcsEventService to treat Webhook Events
    public void handleEvent(String event){
        this.ckcsEventService.handleEvent(event);
    }

    // Deliver token based on personId
    public String getToken(Integer personId){
        PersonShortenDto personDto = this.personService.getShortenWithRoles(personId);
        CKCSUserDto userDto = this.fromPersonToCKCSUser(personDto, personId);
        String token = this.ckcsAuthenticationService.getUserToken(userDto);
        log.info("getToken::"+personId+"::"+token);
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

    // TODO: be adapted
    public String getChannelId(String baseId, Integer type) throws Exception{
        String documentId = "t"+type+"_"+baseId;
        log.info("getChannelId::"+documentId);
        return documentId;
    }

    // TODO: be adapted
    public DocumentDto getDocument(String channelId) {
        //mocked data
        DocumentDto dto = new DocumentDto();
        dto.setChannelId(channelId);
        //t100 - medical notes, soap 1001
        if (channelId.equals("t100_1001")){
            dto.setId(1);
            dto.setContent("<h1><b>Medical Notes</b></h1><br><hr><b>Soap:</b>1001<br>This is my decision about pet:<ol><li>take vaccine 1</li><li>take vaccine 2</li></ol>");
        }
        //t101 - discharged notes, soap 1001
        else if (channelId.equals("t101_1001")){
            dto.setId(2);
            dto.setContent("<h1><b>Discharged Notes</b></h1><br><hr><b>Soap:</b>1001<br>Pet is ok now!");
        }
        //t100 - medical notes, soap 1001
        else if (channelId.equals("t100_1002")){
            dto.setId(1);
            dto.setContent("<h1><b>Medical Notes</b></h1><br><hr><b>Soap:</b>1002<br>This is my decision about pet:<ol><li>NOT take any vaccine</li><li>cleanup ears</li></ol>");
        }
        //t101 - discharged notes, soap 1002
        else if (channelId.equals("t101_1002")){
            dto.setId(4);
            dto.setContent("<h1><b>Discharged Notes</b></h1><br><hr><b>Soap:</b>1002<br>Rest inside home for 3 days!");
        }
        log.info("getDocument::"+dto);
        return dto;
    }

}
