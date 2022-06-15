package com.ckcspoc.ckcspocapigw.controller;

import com.ckcspoc.ckcspocapigw.common.service.CKCSAuthenticationService;
import com.ckcspoc.ckcspocapigw.service.CKCSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class CKCSController {
    private final CKCSService ckcsService;
    private final CKCSAuthenticationService ckcsAuthenticationService;

    @Autowired
    public CKCSController(
            CKCSService ckcsService,
            CKCSAuthenticationService ckcsAuthenticationService) {
        this.ckcsService = ckcsService;
        this.ckcsAuthenticationService = ckcsAuthenticationService;
    }

    // Delivers: User token
    // Triggered by: CKEditor UI component
    // Generates user token based on personId
    @GetMapping("/token")
    public String getToken(@RequestParam(value = "personId") Integer personId) {
        return this.ckcsService.getToken(personId);
    }

    // Delivers: Document Id
    // Triggered by: CKEditor UI component
    // Generates document id based on baseId (example: soapid) and documenttype
    @GetMapping("/documentid")
    public String getDocumentId(@RequestParam(value = "baseId") String baseId,
                                @RequestParam(value = "type") Integer type) throws Exception{
        return this.ckcsService.getDocumentId(baseId, type);
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
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        try{
            this.ckcsAuthenticationService.validateSignature(path, ckcsSignature, ckcsTimestamp, payload);
            this.ckcsService.handleEvent(payload);
            return new ResponseEntity<>(payload, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
