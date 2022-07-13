package com.ckcspoc.ckcspocapigw.service;

import com.ckcspoc.ckcspocapigw.common.dto.event.collaboration.CKCSEventCollaborationSessionUpdatedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.documentstorage.CKCSEventStorageDocumentSavedDto;
import com.ckcspoc.ckcspocapigw.common.service.CKCSAPIIntegrationService;
import com.ckcspoc.ckcspocapigw.common.service.CKCSWebhookService;
import com.ckcspoc.ckcspocapigw.dto.DocumentDto;
import com.ckcspoc.ckcspocapigw.json.JsonGenericBeanConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.swing.text.Document;


@Slf4j
@Service
public class CKCSEventService extends CKCSWebhookService {
    private final CKCSService ckcsService;
    private final CKCSAPIIntegrationService ckcsApiIntegrationService;
    private final SoapService soapService;
    private final Boolean ckcsUsesDocStorage;
    private final Boolean ckcsSyncDocStorage;

    public CKCSEventService(
            CKCSService ckcsService,
            SoapService soapService,
            CKCSAPIIntegrationService ckcsApiIntegrationService,
            JsonGenericBeanConverter jsonGenericBeanConverter,
            @Value("${ckcs-uses-doc-storage}") Boolean ckcsUsesDocStorage,
            @Value("${ckcs-sync-doc-storage}") Boolean ckcsSyncDocStorage) {
        super(jsonGenericBeanConverter);
        this.ckcsService = ckcsService;
        this.ckcsApiIntegrationService = ckcsApiIntegrationService;
        this.soapService = soapService;
        this.ckcsUsesDocStorage = ckcsUsesDocStorage;
        this.ckcsSyncDocStorage = ckcsSyncDocStorage;
    }

    /*
    @Override
    protected void handle(CKCSEventUserConnectedDto dto){
        String documentId = dto.getDocument().getId();
        log.info("CKCSEventService::"+dto);
        log.info("hasToSyncInitialData->"+this.ckcsService.hasToSyncInitialData(documentId));
        log.info("Doc.Storage::"+this.ckcsApiIntegrationService.getDocumentFromStorage(documentId));
    }
    */

    // Document saved at DocStorage
    // Need to sync with rhapsody DB if configured to (ckcs-sync-doc-storage=true)
    protected void handle(CKCSEventStorageDocumentSavedDto dto){
        if (this.ckcsSyncDocStorage){
            String channelId = dto.getDocument().getId();
            String content = this.ckcsApiIntegrationService.getDocumentFromStorage(dto.getDocument().getId());
            this.updateSoapDocument(channelId, content);
        }
    }

    // Document Updated at Collaborativo Session
    // Need to sync with rhapsody DB if configured to (ckcs-uses-doc-storage=false)
    protected void handle(CKCSEventCollaborationSessionUpdatedDto dto){
        if (!this.ckcsUsesDocStorage){
            String channelId = dto.getDocument().getId();
            String content = this.ckcsApiIntegrationService.getCollaboration(dto.getDocument().getId());
            this.updateSoapDocument(channelId, content);
        }
    }

    private void updateSoapDocument(String channelId, String content){
        DocumentDto documentDto = new DocumentDto();
        documentDto.setChannelId(channelId);
        documentDto.setContent(content);
        this.soapService.updateDocument(documentDto);
    }
}
