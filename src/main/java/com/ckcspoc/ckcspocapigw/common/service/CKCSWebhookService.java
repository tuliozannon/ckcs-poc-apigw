package com.ckcspoc.ckcspocapigw.common.service;

import com.ckcspoc.ckcspocapigw.common.dto.event.CKCSEventDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.CKCSEventDtoFactory;
import com.ckcspoc.ckcspocapigw.common.dto.event.collaboration.CKCSEventCollaborationSessionRecoveredDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.collaboration.CKCSEventCollaborationSessionRemovedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.collaboration.CKCSEventCollaborationSessionUpdatedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.collaboration.CKCSEventUserConnectedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.collaboration.CKCSEventUserDisconnectedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.comments.CKCSEventCommentAddedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.comments.CKCSEventCommentRemovedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.comments.CKCSEventCommentThreadRemovedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.comments.CKCSEventCommentThreadRestoredDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.comments.CKCSEventCommentThreadsRemovedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.comments.CKCSEventCommentUpdatedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.documentstorage.CKCSEventStorageDocumentRemovedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.documentstorage.CKCSEventStorageDocumentSaveFailedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.documentstorage.CKCSEventStorageDocumentSavedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.ping.CKCSEventPingDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.trackchanges.CKCSEventSuggestionAcceptedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.trackchanges.CKCSEventSuggestionAddedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.trackchanges.CKCSEventSuggestionRejectedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.trackchanges.CKCSEventSuggestionRemovedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.trackchanges.CKCSEventSuggestionRestoredDto;
import com.ckcspoc.ckcspocapigw.json.JsonGenericBeanConverter;
import com.ckcspoc.ckcspocapigw.json.RawJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CKCSWebhookService {
    private final JsonGenericBeanConverter jsonGenericBeanConverter;
    protected final CKCSAPIIntegrationService ckcsapiIntegrationService;


    public CKCSWebhookService(JsonGenericBeanConverter jsonGenericBeanConverter,
                              CKCSAPIIntegrationService ckcsapiIntegrationService){
        this.jsonGenericBeanConverter = jsonGenericBeanConverter;
        this.ckcsapiIntegrationService = ckcsapiIntegrationService;
    }

    public final CKCSEventDto handleEvent(String eventStr){
        CKCSEventDto dto = this.jsonGenericBeanConverter.as(eventStr, CKCSEventDto.class);
        Class payloadClss = CKCSEventDtoFactory.getCKCSEventDtoClass(dto.getEvent());
        dto.setPayload(this.jsonGenericBeanConverter.convert(dto.getPayload(), RawJson.class).as(payloadClss));
        this.handle(dto);
        return dto;
    }

    protected final void handle(CKCSEventDto dto){
        log.info("CKCSWebhookService::handle("+dto.getEvent()+") "+dto);
        switch (dto.getEvent()){
            // Ping Event
            case CKCSEventPingDto.EVENT_KEY:            this.handle((CKCSEventPingDto) dto.getPayload());            break;

            // Comments Events
            case CKCSEventCommentAddedDto.EVENT_KEY:            this.handle((CKCSEventCommentAddedDto) dto.getPayload());            break;
            case CKCSEventCommentUpdatedDto.EVENT_KEY:          this.handle((CKCSEventCommentUpdatedDto) dto.getPayload());          break;
            case CKCSEventCommentRemovedDto.EVENT_KEY:          this.handle((CKCSEventCommentRemovedDto) dto.getPayload());          break;
            case CKCSEventCommentThreadRemovedDto.EVENT_KEY:    this.handle((CKCSEventCommentThreadRemovedDto) dto.getPayload());    break;
            case CKCSEventCommentThreadRestoredDto.EVENT_KEY:   this.handle((CKCSEventCommentThreadRestoredDto) dto.getPayload());   break;
            case CKCSEventCommentThreadsRemovedDto.EVENT_KEY:   this.handle((CKCSEventCommentThreadsRemovedDto) dto.getPayload());   break;

            // Collaboration Events
            case CKCSEventUserConnectedDto.EVENT_KEY:                   this.handle((CKCSEventUserConnectedDto) dto.getPayload());                   break;
            case CKCSEventUserDisconnectedDto.EVENT_KEY:                this.handle((CKCSEventUserDisconnectedDto) dto.getPayload());                break;
            case CKCSEventCollaborationSessionUpdatedDto.EVENT_KEY:     this.handle((CKCSEventCollaborationSessionUpdatedDto) dto.getPayload());     break;
            case CKCSEventCollaborationSessionRemovedDto.EVENT_KEY:     this.handle((CKCSEventCollaborationSessionRemovedDto) dto.getPayload());     break;
            case CKCSEventCollaborationSessionRecoveredDto.EVENT_KEY:   this.handle((CKCSEventCollaborationSessionRecoveredDto) dto.getPayload());   break;

            // Document Storage Events
            case CKCSEventStorageDocumentSavedDto.EVENT_KEY:        this.handle((CKCSEventStorageDocumentSavedDto) dto.getPayload());        break;
            case CKCSEventStorageDocumentSaveFailedDto.EVENT_KEY:   this.handle((CKCSEventStorageDocumentSaveFailedDto) dto.getPayload());   break;
            case CKCSEventStorageDocumentRemovedDto.EVENT_KEY:      this.handle((CKCSEventStorageDocumentRemovedDto) dto.getPayload());      break;

            // Track Changes Events
            case CKCSEventSuggestionAddedDto.EVENT_KEY:     this.handle((CKCSEventSuggestionAddedDto) dto.getPayload());     break;
            case CKCSEventSuggestionAcceptedDto.EVENT_KEY:  this.handle((CKCSEventSuggestionAcceptedDto) dto.getPayload());  break;
            case CKCSEventSuggestionRejectedDto.EVENT_KEY:  this.handle((CKCSEventSuggestionRejectedDto) dto.getPayload());  break;
            case CKCSEventSuggestionRemovedDto.EVENT_KEY:   this.handle((CKCSEventSuggestionRemovedDto) dto.getPayload());   break;
            case CKCSEventSuggestionRestoredDto.EVENT_KEY:  this.handle((CKCSEventSuggestionRestoredDto) dto.getPayload());  break;
        }
    }

    //Ping Events
    protected void handle(CKCSEventPingDto payload) {}

    //Comments Events
    protected void handle(CKCSEventCommentAddedDto dto){}
    protected void handle(CKCSEventCommentUpdatedDto dto){}
    protected void handle(CKCSEventCommentRemovedDto dto){}
    protected void handle(CKCSEventCommentThreadRemovedDto dto){}
    protected void handle(CKCSEventCommentThreadRestoredDto dto){}
    protected void handle(CKCSEventCommentThreadsRemovedDto dto){}

    // Collaboration Events
    protected void handle(CKCSEventUserConnectedDto dto){}
    protected void handle(CKCSEventUserDisconnectedDto dto){}
    protected void handle(CKCSEventCollaborationSessionUpdatedDto dto){}
    protected void handle(CKCSEventCollaborationSessionRemovedDto dto){}
    protected void handle(CKCSEventCollaborationSessionRecoveredDto dto){}

    // Document Storage Events
    protected void handle(CKCSEventStorageDocumentSavedDto dto){}
    protected void handle(CKCSEventStorageDocumentSaveFailedDto dto){}
    protected void handle(CKCSEventStorageDocumentRemovedDto dto){}

    // Track Changes Events
    protected void handle(CKCSEventSuggestionAddedDto dto){}
    protected void handle(CKCSEventSuggestionAcceptedDto dto){}
    protected void handle(CKCSEventSuggestionRejectedDto dto){}
    protected void handle(CKCSEventSuggestionRemovedDto dto){}
    protected void handle(CKCSEventSuggestionRestoredDto dto){}

}
