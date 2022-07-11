package com.ckcspoc.ckcspocapigw.controller;

import com.ckcspoc.ckcspocapigw.common.service.CKCSAPIIntegrationService;
import com.ckcspoc.ckcspocapigw.common.service.CKCSAuthenticationService;
import com.ckcspoc.ckcspocapigw.common.util.CKCSConstants;
import com.ckcspoc.ckcspocapigw.dto.DocumentDto;
import com.ckcspoc.ckcspocapigw.service.CKCSService;
import com.ckcspoc.ckcspocapigw.service.SoapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("ckcs")
@CrossOrigin
public class CKCSController {
    private final CKCSService ckcsService;
    private final CKCSAuthenticationService ckcsAuthenticationService;
    private final SoapService soapService;

    @Autowired
    public CKCSController(
            CKCSService ckcsService,
            CKCSAuthenticationService ckcsAuthenticationService,
            CKCSAPIIntegrationService ckcsAPIIntegrationService,
            SoapService soapService) {
        this.ckcsService = ckcsService;
        this.ckcsAuthenticationService = ckcsAuthenticationService;
        this.soapService = soapService;
    }

    // Delivers: User token
    // Triggered by: CKEditor UI component
    // Generates user token based on personId
    @GetMapping("/token")
    public String getToken(@RequestParam(value = "personId") Integer personId) {
        return this.ckcsService.getToken(personId);
    }

    // Delivers: Channel Id
    // Triggered by: CKEditor UI component
    // Generates channelId (documentId) based on baseId (example: soapid) and type (example: medical or discharged note)
    @GetMapping("/channelId")
    public String getChannelId(@RequestParam(value = "baseId") Integer baseId,
                                @RequestParam(value = "type") Integer type) throws Exception{
        log.info("CKCSController::getChannelId::"+baseId+"::"+type);
        return this.soapService.getChannelId(baseId, type);
    }

    // Delivers: Document (initial data)
    // Triggered by: CKEditor UI component
    // Delivers DocumentDto based on a channelId
    @GetMapping("/document")
    public DocumentDto getDocument(@RequestParam(value = "channelId") String channelId) {
        log.info("CKCSController::getDocument::"+channelId);
        return this.soapService.getDocument(channelId);
    }


    // Delivers: HTTP Status Response
    // Triggered by: CKEditor Cloud Services
    // Treat event triggered by cloud services (logic depends on event value)
    @PostMapping("/webhook")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> execWebhook(@RequestHeader("X-CS-Signature") String ckcsSignature,
                                              @RequestHeader("X-CS-Timestamp") String ckcsTimestamp,
                                              @RequestBody String payload,
                                              HttpServletRequest request) {
        log.info("CKCSController::execWebhook::[EVENT]");
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        try{
            this.ckcsAuthenticationService.validateSignature(CKCSConstants.POST, path, ckcsSignature, ckcsTimestamp, payload);
            this.ckcsService.handleEvent(payload);
            return new ResponseEntity<>(payload, HttpStatus.OK);
        }
        catch(Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
