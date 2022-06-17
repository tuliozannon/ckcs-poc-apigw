package com.ckcspoc.ckcspocapigw.common.service;

import com.ckcspoc.ckcspocapigw.client.CKCSClient;
import com.ckcspoc.ckcspocapigw.common.dto.CKCSAPIHeaderDto;
import com.ckcspoc.ckcspocapigw.common.util.CKCSConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CKCSAPIIntegrationService {
    private final CKCSAuthenticationService ckcsAuthenticationService;
    private final CKCSClient ckcsClient;


    public CKCSAPIIntegrationService(CKCSAuthenticationService ckcsAuthenticationService,
                                     CKCSClient ckcsClient) {
        this.ckcsAuthenticationService = ckcsAuthenticationService;
        this.ckcsClient = ckcsClient;
    }

    public Object isCollaborationSessionExists(String documentId) {
        String path = "/collaborations/"+documentId+"/exists";
        CKCSAPIHeaderDto header = this.ckcsAuthenticationService.getAPIHeader(CKCSConstants.GET, path);
        return this.ckcsClient.isCollaborationSessionExists(header.getSignature(), header.getTimestamp(), documentId);
    }

    public Object getCollaborations() {
        String path = "/collaborations";
        CKCSAPIHeaderDto header = this.ckcsAuthenticationService.getAPIHeader(CKCSConstants.GET, path);
        return this.ckcsClient.getCollaborations(header.getSignature(), header.getTimestamp());
    }

    public Object getCollaborationDetails(String documentId) {
        String path = "/collaborations/"+documentId+"/details";
        CKCSAPIHeaderDto header = this.ckcsAuthenticationService.getAPIHeader(CKCSConstants.GET, path);
        return this.ckcsClient.getCollaborationDetails(header.getSignature(), header.getTimestamp(), documentId);
    }

    public Object deleteCollaboration(String documentId, Boolean force) {
        if (force==null) force = false;
        String path = "/collaborations/"+documentId+"?force="+force;

        CKCSAPIHeaderDto header = this.ckcsAuthenticationService.getAPIHeader(CKCSConstants.DELETE, path);
        return this.ckcsClient.deleteCollaboration(header.getSignature(), header.getTimestamp(), documentId, force);
    }

    public Object deleteCollaborations(Boolean force) {
        if (force==null) force = false;
        String path = "/collaborations?force="+force;

        CKCSAPIHeaderDto header = this.ckcsAuthenticationService.getAPIHeader(CKCSConstants.DELETE, path);
        return this.ckcsClient.deleteCollaborations(header.getSignature(), header.getTimestamp(), force);
    }
}
