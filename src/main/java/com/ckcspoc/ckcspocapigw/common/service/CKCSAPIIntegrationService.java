package com.ckcspoc.ckcspocapigw.common.service;

import com.ckcspoc.ckcspocapigw.client.CKCSClient;
import com.ckcspoc.ckcspocapigw.common.dto.CKCSRequestDto;
import com.ckcspoc.ckcspocapigw.common.dto.editor.EditorBundleDto;
import com.ckcspoc.ckcspocapigw.common.util.CKCSConstants;
import com.ckcspoc.ckcspocapigw.common.dto.CKCSCollaborativeSessionDto;
import feign.FeignException;
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
        CKCSRequestDto request = this.ckcsAuthenticationService.getRequest(CKCSConstants.GET, path);
        return this.ckcsClient.getCollaborations(request.getSignature(), request.getTimestamp());
    }

    public Boolean isCollaborationExists(String documentId) {
        String path = "/collaborations/"+documentId+"/exists";
        CKCSRequestDto request = this.ckcsAuthenticationService.getRequest(CKCSConstants.GET, path);
        Object object = this.ckcsClient.isCollaborationExists(request.getSignature(), request.getTimestamp(), documentId);
        return (object != null) && (((Map)object).get("exists").equals(true));
    }

    public String getCollaboration(String documentId) {
        String path = "/collaborations/"+documentId;

        CKCSRequestDto request = this.ckcsAuthenticationService.getRequest(CKCSConstants.GET, path);
        return this.ckcsClient.getCollaboration(request.getSignature(), request.getTimestamp(), documentId);
    }

    public Object getCollaborationDetails(String documentId) {
        String path = "/collaborations/"+documentId+"/details";
        CKCSRequestDto request = this.ckcsAuthenticationService.getRequest(CKCSConstants.GET, path);
        return this.ckcsClient.getCollaborationDetails(request.getSignature(), request.getTimestamp(), documentId);
    }

    public Object flushCollaboration(String documentId, Boolean force) {
        if (force==null) force = false;
        String path = "/collaborations/"+documentId+"?wait=true&force="+force;

        CKCSRequestDto request = this.ckcsAuthenticationService.getRequest(CKCSConstants.DELETE, path);
        return this.ckcsClient.flushCollaboration(request.getSignature(), request.getTimestamp(), documentId, force);
    }

    public Object flushCollaborations(Boolean force) {
        if (force==null) force = false;
        String path = "/collaborations?force="+force;

        CKCSRequestDto request = this.ckcsAuthenticationService.getRequest(CKCSConstants.DELETE, path);
        return this.ckcsClient.flushCollaborations(request.getSignature(), request.getTimestamp(), force);
    }

    public Object createCollaboration(CKCSCollaborativeSessionDto dto) {
        String path = "/collaborations";
        CKCSRequestDto request = this.ckcsAuthenticationService.getRequest(CKCSConstants.POST, path, dto);
        return this.ckcsClient.createCollaboration(request.getSignature(), request.getTimestamp(), request.getBody());
    }

    public Object restoreCollaboration(String documentId) {
        String path = "/collaborations";
        CKCSRequestDto request = this.ckcsAuthenticationService.getRequest(CKCSConstants.PUT, path);
        return this.ckcsClient.createCollaboration(request.getSignature(), request.getTimestamp(), documentId);
    }

    /**************************************************************************
     * EDITOR BUNDLE
     **************************************************************************/
    public Object isEditorBundleExists(String bundle_version) {
        String path = "/editors/"+bundle_version+"/exists";
        CKCSRequestDto request = this.ckcsAuthenticationService.getRequest(CKCSConstants.GET, path);
        return this.ckcsClient.isEditorBundleExists(request.getSignature(), request.getTimestamp(), bundle_version);
    }

    public Object uploadEditorBundle(EditorBundleDto dto) {
        String path = "/editors/";
        String editorBundleName = dto.getConfig().getCloudServices().getBundleVersion();
        String editorBundleJS = this.ckcsEditorBundleService.getEditorBundleFromResource(editorBundleName);
        dto.setBundle(editorBundleJS);
        CKCSRequestDto request = this.ckcsAuthenticationService.getRequest(CKCSConstants.POST, path, dto);
        return this.ckcsClient.uploadEditorBundle(request.getSignature(), request.getTimestamp(), request.getBody());
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

        CKCSRequestDto request = this.ckcsAuthenticationService.getRequest(CKCSConstants.GET, path);
        return this.ckcsClient.getDocumentsFromStorage(request.getSignature(), request.getTimestamp(), limit, sortBy, order, cursor);
    }

    public String getDocumentFromStorage(String documentId) {
        String path = "/storage/"+documentId;

        CKCSRequestDto request = this.ckcsAuthenticationService.getRequest(CKCSConstants.GET, path);
        return this.ckcsClient.getDocumentFromStorage(request.getSignature(), request.getTimestamp(), documentId);
    }

    public Boolean isDocumentAtStorage(String documentId) {
        try{
            String content = this.getDocumentFromStorage(documentId);
            return (content!=null);
        }
        catch(FeignException.NotFound fe){
            return false;
        }
    }

    public Object deleteDocumentFromStorage(String documentId) {
        String path = "/storage/"+documentId;

        CKCSRequestDto request = this.ckcsAuthenticationService.getRequest(CKCSConstants.DELETE, path);
        return this.ckcsClient.deleteDocumentFromStorage(request.getSignature(), request.getTimestamp(), documentId);
    }


}
