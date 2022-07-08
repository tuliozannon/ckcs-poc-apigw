package com.ckcspoc.ckcspocapigw.common.service;

import com.ckcspoc.ckcspocapigw.client.CKCSClient;
import com.ckcspoc.ckcspocapigw.common.dto.CKCSAPIHeaderDto;
import com.ckcspoc.ckcspocapigw.common.dto.editor.EditorBundleDto;
import com.ckcspoc.ckcspocapigw.common.util.CKCSConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;


@Slf4j
@Service
public class CKCSAPIIntegrationService {
    private final CKCSAuthenticationService ckcsAuthenticationService;
    private final CKCSEditorBundleService ckcsEditorBundleService;
    private final CKCSClient ckcsClient;


    public CKCSAPIIntegrationService(CKCSAuthenticationService ckcsAuthenticationService,
                                     CKCSEditorBundleService ckcsEditorBundleService,
                                     CKCSClient ckcsClient) {
        this.ckcsAuthenticationService = ckcsAuthenticationService;
        this.ckcsEditorBundleService = ckcsEditorBundleService;
        this.ckcsClient = ckcsClient;
    }

    /**************************************************************************
     * COLLABORATIONS
     **************************************************************************/
    public Object getCollaborations() {
        String path = "/collaborations";
        CKCSAPIHeaderDto header = this.ckcsAuthenticationService.getAPIHeader(CKCSConstants.GET, path);
        return this.ckcsClient.getCollaborations(header.getSignature(), header.getTimestamp());
    }

    public Object isCollaborationExists(String documentId) {
        String path = "/collaborations/"+documentId+"/exists";
        CKCSAPIHeaderDto header = this.ckcsAuthenticationService.getAPIHeader(CKCSConstants.GET, path);
        return this.ckcsClient.isCollaborationExists(header.getSignature(), header.getTimestamp(), documentId);
    }

    public String getCollaboration(String documentId) {
        String path = "/collaborations/"+documentId;

        CKCSAPIHeaderDto header = this.ckcsAuthenticationService.getAPIHeader(CKCSConstants.GET, path);
        return this.ckcsClient.getCollaboration(header.getSignature(), header.getTimestamp(), documentId);
    }

    public Object getCollaborationDetails(String documentId) {
        String path = "/collaborations/"+documentId+"/details";
        CKCSAPIHeaderDto header = this.ckcsAuthenticationService.getAPIHeader(CKCSConstants.GET, path);
        return this.ckcsClient.getCollaborationDetails(header.getSignature(), header.getTimestamp(), documentId);
    }

    public Object flushCollaboration(String documentId, Boolean force) {
        if (force==null) force = false;
        String path = "/collaborations/"+documentId+"?wait=true&force="+force;

        CKCSAPIHeaderDto header = this.ckcsAuthenticationService.getAPIHeader(CKCSConstants.DELETE, path);
        return this.ckcsClient.flushCollaboration(header.getSignature(), header.getTimestamp(), documentId, force);
    }

    public Object flushCollaborations(Boolean force) {
        if (force==null) force = false;
        String path = "/collaborations?force="+force;

        CKCSAPIHeaderDto header = this.ckcsAuthenticationService.getAPIHeader(CKCSConstants.DELETE, path);
        return this.ckcsClient.flushCollaborations(header.getSignature(), header.getTimestamp(), force);
    }

    /**************************************************************************
     * EDITOR BUNDLE
     **************************************************************************/
    public Object isEditorBundleExists(String bundle_version) {
        String path = "/editors/"+bundle_version+"/exists";
        CKCSAPIHeaderDto header = this.ckcsAuthenticationService.getAPIHeader(CKCSConstants.GET, path);
        return this.ckcsClient.isEditorBundleExists(header.getSignature(), header.getTimestamp(), bundle_version);
    }

    public Object uploadEditorBundle(EditorBundleDto editorBundle) {
        String path = "/editors/";
        if (editorBundle.getBundle().equals("@bundle_js@")){
            String editorBundleName = editorBundle.getConfig().getCloudServices().getBundleVersion();
            String editorBundleJS = this.ckcsEditorBundleService.getEditorBundleFromResource(editorBundleName);
            editorBundle.setBundle(editorBundleJS);
        }
        CKCSAPIHeaderDto header = this.ckcsAuthenticationService.getAPIHeader(CKCSConstants.POST, path, editorBundle);
        return this.ckcsClient.uploadEditorBundle(header.getSignature(), header.getTimestamp(), header.getBody());
    }

    /**************************************************************************
     * STORAGE
     **************************************************************************/
    public Object getDocumentsFromStorage(Integer limit, String sortBy, String order, String cursor) {
        if (limit==null) limit = 100;
        if (sortBy==null) sortBy = "created_at";
        if (order==null) order = "asc";

        String path = "/storage?limit="+limit+"&sort_by="+sortBy+"&order="+order;
        if (cursor != null) path += "&cursor="+cursor;

        CKCSAPIHeaderDto header = this.ckcsAuthenticationService.getAPIHeader(CKCSConstants.GET, path);
        return this.ckcsClient.getDocumentsFromStorage(header.getSignature(), header.getTimestamp(), limit, sortBy, order, cursor);
    }

    public String getDocumentFromStorage(String documentId) {
        String path = "/storage/"+documentId;

        CKCSAPIHeaderDto header = this.ckcsAuthenticationService.getAPIHeader(CKCSConstants.GET, path);
        return this.ckcsClient.getDocumentFromStorage(header.getSignature(), header.getTimestamp(), documentId);
    }

}
