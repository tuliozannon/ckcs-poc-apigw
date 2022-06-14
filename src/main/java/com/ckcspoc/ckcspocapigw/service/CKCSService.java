package com.ckcspoc.ckcspocapigw.service;

import com.ckcspoc.ckcspocapigw.dto.CKCSWebhookEventDto;
import com.ckcspoc.ckcspocapigw.util.JsonGenericBeanConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CKCSService {
    private final JsonGenericBeanConverter jsonGenericBeanConverter;

    public CKCSService(
            JsonGenericBeanConverter jsonGenericBeanConverter
        ){
        this.jsonGenericBeanConverter = jsonGenericBeanConverter;
    }

    //TODO: be adapted on real system
    public String getDocumentId(String baseId, Integer type) throws Exception{
        String documentId = "t"+type+"_"+baseId;
        return documentId;
    }

    public CKCSWebhookEventDto execWebhook(String eventStr){
        CKCSWebhookEventDto dto = this.jsonGenericBeanConverter.as(eventStr, CKCSWebhookEventDto.class);
        return dto;
    }

}
