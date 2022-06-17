package com.ckcspoc.ckcspocapigw.controller;

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

    @GetMapping("/collaborations")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getCollaborations(){
        log.info("getCollaborations::");
        Object exists = this.ckcsAPIIntegrationService.getCollaborations();
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    @GetMapping("/collaborations/{documentId}/exists")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> isCollaborationSessionExists(@PathVariable(value = "documentId") String documentId){
        log.info("isCollaborationSessionExists::"+documentId);
        Object exists = this.ckcsAPIIntegrationService.isCollaborationSessionExists(documentId);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    @GetMapping("/collaborations/{documentId}/details")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getCollaborationDetails(@PathVariable(value = "documentId") String documentId){
        log.info("getCollaborationDetails::"+documentId);
        Object exists = this.ckcsAPIIntegrationService.getCollaborationDetails(documentId);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    @DeleteMapping("/collaborations/{documentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteCollaboration(@PathVariable(value = "documentId") String documentId){
        log.info("deleteCollaboration::"+documentId);
        Object exists = this.ckcsAPIIntegrationService.deleteCollaboration(documentId, true);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    @DeleteMapping("/collaborations")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteCollaborations(){
        log.info("deleteCollaborations::");
        Object exists = this.ckcsAPIIntegrationService.deleteCollaborations(true);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
}
