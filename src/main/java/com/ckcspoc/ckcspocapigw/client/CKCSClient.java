package com.ckcspoc.ckcspocapigw.client;

import com.ckcspoc.ckcspocapigw.common.config.FeignConfig;
import com.ckcspoc.ckcspocapigw.common.dto.editor.EditorBundleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ckcs-client", url = "${ckcs-client-api-url}", configuration = FeignConfig.class)
public interface CKCSClient {

    /**************************************************************************
     * COLLABORATIONS
     **************************************************************************/
    //Get a list of documents with collaborative editing sessions
    @GetMapping("/collaborations")
    Object getCollaborations(
            @RequestHeader("X-CS-Signature") String xcsSignature,
            @RequestHeader("X-CS-Timestamp") String xcsTimestamp);


    //Check if a specific collaborative editing session exists
    @GetMapping("/collaborations/{documentId}/exists")
    Object isCollaborationExists(
            @RequestHeader("X-CS-Signature") String xcsSignature,
            @RequestHeader("X-CS-Timestamp") String xcsTimestamp,
            @PathVariable String documentId);

    //Get a document from collaborative session
    @GetMapping("/collaborations/{documentId}")
    String getCollaboration(
            @RequestHeader("X-CS-Signature") String xcsSignature,
            @RequestHeader("X-CS-Timestamp") String xcsTimestamp,
            @PathVariable String documentId);

    //Get details about a specific collaborative session
    @GetMapping("/collaborations/{documentId}/details")
    Object getCollaborationDetails(
            @RequestHeader("X-CS-Signature") String xcsSignature,
            @RequestHeader("X-CS-Timestamp") String xcsTimestamp,
            @PathVariable String documentId);

    //Flush a collaborative editing session
    @DeleteMapping("/collaborations/{documentId}?wait=true&force={force}")
    Object flushCollaboration(
            @RequestHeader("X-CS-Signature") String xcsSignature,
            @RequestHeader("X-CS-Timestamp") String xcsTimestamp,
            @PathVariable String documentId,
            @PathVariable Boolean force);

    //Flush all collaborative editing sessions
    @DeleteMapping("/collaborations?force={force}")
    Object flushCollaborations(
            @RequestHeader("X-CS-Signature") String xcsSignature,
            @RequestHeader("X-CS-Timestamp") String xcsTimestamp,
            @PathVariable Boolean force);

    //Create a collaborative editing session
    @PostMapping("/collaborations")
    Object createCollaboration(
            @RequestHeader("X-CS-Signature") String xcsSignature,
            @RequestHeader("X-CS-Timestamp") String xcsTimestamp,
            @RequestBody String collaborationData);

    /**************************************************************************
     * COMMENTS
     **************************************************************************/
    //Get a list of comments
    @GetMapping("/comments?document_id={document_id}&included_deleted={include_deleted}&limit={limit}&sort_by={sortBy}&order={order}&cursor={cursor}")
    Object getComments(
            @RequestHeader("X-CS-Signature") String xcsSignature,
            @RequestHeader("X-CS-Timestamp") String xcsTimestamp,
            @PathVariable String documentId,
            @PathVariable Boolean includedDeleted,
            @PathVariable Integer limit,
            @PathVariable String sortBy,
            @PathVariable String order
    );


    //Get a comment
    @GetMapping("/comments/{commentId}?include_deleted={includeDeleted}&document_id={documentId}")
    String getComment(
            @RequestHeader("X-CS-Signature") String xcsSignature,
            @RequestHeader("X-CS-Timestamp") String xcsTimestamp,
            @PathVariable String commentId,
            @PathVariable Boolean includeDeleted);

    /**************************************************************************
     * SUGGESTIONS
     **************************************************************************/
    //Get a list of suggestions
    @GetMapping("/suggestions?included_deleted={include_deleted}&limit={limit}&sort_by={sortBy}&order={order}&cursor={cursor}&document_id={document_id}&thread_id={thread_id}")
    Object getSuggestions(
            @RequestHeader("X-CS-Signature") String xcsSignature,
            @RequestHeader("X-CS-Timestamp") String xcsTimestamp,
            @PathVariable String documentId,
            @PathVariable Boolean includedDeleted,
            @PathVariable Integer limit,
            @PathVariable String sortBy,
            @PathVariable String order
    );

    //Get a suggestion
    @GetMapping("/suggestions/{suggestionId}?include_deleted={includeDeleted}")
    String getSuggestion(
            @RequestHeader("X-CS-Signature") String xcsSignature,
            @RequestHeader("X-CS-Timestamp") String xcsTimestamp,
            @PathVariable String suggestionId,
            @PathVariable Boolean includeDeleted);

    /**************************************************************************
     * EDITOR BUNDLE
     **************************************************************************/
    //Check if a specific editor bundle exists
    @GetMapping("/editors/{bundle_version}/exists")
    Object isEditorBundleExists(
            @RequestHeader("X-CS-Signature") String xcsSignature,
            @RequestHeader("X-CS-Timestamp") String xcsTimestamp,
            @PathVariable String bundle_version);

    //Upload an editor bundle
    @PostMapping(value="/editors/")
    Object uploadEditorBundle(
            @RequestHeader("X-CS-Signature") String xcsSignature,
            @RequestHeader("X-CS-Timestamp") String xcsTimestamp,
            @RequestBody String editorBundle);

    /**************************************************************************
     * STORAGE
     **************************************************************************/
    //Get a list of documents from Storage
    @GetMapping("/storage?limit={limit}&sort_by={sortBy}&order={order}&cursor={cursor}")
    Object getDocumentsFromStorage(
            @RequestHeader("X-CS-Signature") String xcsSignature,
            @RequestHeader("X-CS-Timestamp") String xcsTimestamp,
            @PathVariable Integer limit,
            @PathVariable String sortBy,
            @PathVariable String order,
            @PathVariable String cursor);

    //Get a specific document from Storage
    @GetMapping("/storage/{documentId}")
    String getDocumentFromStorage(
            @RequestHeader("X-CS-Signature") String xcsSignature,
            @RequestHeader("X-CS-Timestamp") String xcsTimestamp,
            @PathVariable String documentId);

    //Delete a specific document from Storage
    @DeleteMapping("/storage/{documentId}")
    Object deleteDocumentFromStorage(
            @RequestHeader("X-CS-Signature") String xcsSignature,
            @RequestHeader("X-CS-Timestamp") String xcsTimestamp,
            @PathVariable String documentId);

}
