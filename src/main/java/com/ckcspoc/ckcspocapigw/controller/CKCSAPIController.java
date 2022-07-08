package com.ckcspoc.ckcspocapigw.controller;

import com.ckcspoc.ckcspocapigw.common.dto.editor.EditorBundleDto;
import com.ckcspoc.ckcspocapigw.common.service.CKCSAPIIntegrationService;
import com.ckcspoc.ckcspocapigw.common.service.CKCSAuthenticationService;
import com.ckcspoc.ckcspocapigw.dto.DocumentDto;
import com.ckcspoc.ckcspocapigw.service.CKCSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

@Slf4j
@RestController
@RequestMapping("ckcs-api")
@CrossOrigin
public class CKCSAPIController {
    private final CKCSAPIIntegrationService ckcsAPIIntegrationService;

    @Autowired
    public CKCSAPIController(
            CKCSService ckcsService,
            CKCSAuthenticationService ckcsAuthenticationService,
            CKCSAPIIntegrationService ckcsAPIIntegrationService) {
        this.ckcsAPIIntegrationService = ckcsAPIIntegrationService;
    }

    /**************************************************************************
     * COLLABORATIONS
     **************************************************************************/
    @GetMapping("/collaborations")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getCollaborations(){
        log.info("getCollaborations::");
        Object response = this.ckcsAPIIntegrationService.getCollaborations();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/collaborations/{documentId}/exists")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> isCollaborationExists(@PathVariable(value = "documentId") String documentId){
        log.info("isCollaborationExists::"+documentId);
        Object response = this.ckcsAPIIntegrationService.isCollaborationExists(documentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/collaborations/{documentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getCollaboration(@PathVariable(value = "documentId") String documentId){
        log.info("getCollaboration::");
        String response = this.ckcsAPIIntegrationService.getCollaboration(documentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/collaborations/{documentId}/details")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getCollaborationDetails(@PathVariable(value = "documentId") String documentId){
        log.info("getCollaborationDetails::"+documentId);
        Object response = this.ckcsAPIIntegrationService.getCollaborationDetails(documentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/collaborations/{documentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> flushCollaboration(@PathVariable(value = "documentId") String documentId){
        log.info("deleteCollaboration::"+documentId);
        Object response = this.ckcsAPIIntegrationService.flushCollaboration(documentId, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/collaborations")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> flushCollaborations(){
        log.info("deleteCollaborations::");
        Object response = this.ckcsAPIIntegrationService.flushCollaborations(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**************************************************************************
     * EDITOR BUNDLE
     **************************************************************************/
    @GetMapping("/editors/{bundle_version}/exists")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> isEditorBundleExists(@PathVariable(value = "bundle_version") String bundle_version){
        log.info("isEditorBundleExists::"+bundle_version);
        Object response = this.ckcsAPIIntegrationService.isEditorBundleExists(bundle_version);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/editors")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> uploadEditorBundle(@RequestBody EditorBundleDto editorBundle) {
        Object response = this.ckcsAPIIntegrationService.uploadEditorBundle(editorBundle);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**************************************************************************
     * STORAGE
     **************************************************************************/
    @GetMapping("/storage")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getDocumentsFromStorage(
            @RequestParam(required = false, value = "limit") Integer limit,
            @RequestParam(required = false, value = "sort_by") String sortBy,
            @RequestParam(required = false, value = "order") String order,
            @RequestParam(required = false, value = "cursor") String cursor){
        log.info("getDocuments::limit::"+limit);
        log.info("getDocuments::sort_by::"+sortBy);
        log.info("getDocuments::order::"+order);
        log.info("getDocuments::cursor::"+cursor);
        Object response = this.ckcsAPIIntegrationService.getDocumentsFromStorage(limit, sortBy, order, cursor);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/storage/{documentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getDocumentFromStorage(@PathVariable(value = "documentId") String documentId){
        log.info("getDocumentFromStorage::");
        String response = this.ckcsAPIIntegrationService.getDocumentFromStorage(documentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
