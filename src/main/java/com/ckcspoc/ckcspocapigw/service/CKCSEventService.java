package com.ckcspoc.ckcspocapigw.service;

import com.ckcspoc.ckcspocapigw.common.dto.event.collaboration.CKCSEventCollaborationSessionUpdatedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.collaboration.CKCSEventUserConnectedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.ping.CKCSEventPingDto;
import com.ckcspoc.ckcspocapigw.common.service.CKCSAPIIntegrationService;
import com.ckcspoc.ckcspocapigw.common.service.CKCSWebhookService;
import com.ckcspoc.ckcspocapigw.json.JsonGenericBeanConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CKCSEventService extends CKCSWebhookService {

    public CKCSEventService(JsonGenericBeanConverter jsonGenericBeanConverter,
                            CKCSAPIIntegrationService ckcsapiIntegrationService) {
        super(jsonGenericBeanConverter, ckcsapiIntegrationService);
    }

    // Collaboration Events
    @Override
    protected void handle(CKCSEventUserConnectedDto dto){
        log.info("CKCSEventService::"+dto);
    }

    @Override
    protected void handle(CKCSEventCollaborationSessionUpdatedDto dto){
        log.info("CKCSEventService::"+dto);
        // When collaborative session is updated
        // Force flush sessions, in this case, data will
        // if Doc. Storage is turned on, it will persist data
        //this.ckcsapiIntegrationService.flushCollaboration(dto.getDocument().getId(), true);

    }

    @Override
    protected void handle(CKCSEventPingDto dto){
        log.info("CKCSEventService::"+dto);
    }

}
