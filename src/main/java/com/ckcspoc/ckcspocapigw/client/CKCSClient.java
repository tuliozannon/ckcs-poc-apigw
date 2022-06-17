package com.ckcspoc.ckcspocapigw.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ckcs-client", url = "${ckcs-client-api-url}")
public interface CKCSClient {

    //Check if a collaborative editing session exists
    @GetMapping("/collaborations/{documentId}/exists")
    Object isCollaborationSessionExists(
            @RequestHeader("X-CS-Signature") String xcsSignature,
            @RequestHeader("X-CS-Timestamp") String xcsTimestamp,
            @PathVariable String documentId);

    //Get a list of documents with collaborative editing sessions
    @GetMapping("/collaborations")
    Object getCollaborations(
            @RequestHeader("X-CS-Signature") String xcsSignature,
            @RequestHeader("X-CS-Timestamp") String xcsTimestamp);

    //Get details about document sessions
    @GetMapping("/collaborations/{documentId}/details")
    Object getCollaborationDetails(
            @RequestHeader("X-CS-Signature") String xcsSignature,
            @RequestHeader("X-CS-Timestamp") String xcsTimestamp,
            @PathVariable String documentId);

    //Flush a collaborative editing session
    @DeleteMapping("/collaborations/{documentId}?force={force}")
    Object deleteCollaboration(
            @RequestHeader("X-CS-Signature") String xcsSignature,
            @RequestHeader("X-CS-Timestamp") String xcsTimestamp,
            @PathVariable String documentId,
            @PathVariable Boolean force);

    //Flush all collaborative editing sessions
    @DeleteMapping("/collaborations?force={force}")
    Object deleteCollaborations(
            @RequestHeader("X-CS-Signature") String xcsSignature,
            @RequestHeader("X-CS-Timestamp") String xcsTimestamp,
            @PathVariable Boolean force);
}
