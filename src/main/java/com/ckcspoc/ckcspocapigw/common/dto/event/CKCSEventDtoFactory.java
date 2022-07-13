package com.ckcspoc.ckcspocapigw.common.dto.event;

import com.ckcspoc.ckcspocapigw.common.dto.event.collaboration.CKCSEventCollaborationSessionUpdatedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.collaboration.CKCSEventUserConnectedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.ping.CKCSEventPingDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.trackchanges.CKCSEventSuggestionAcceptedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.trackchanges.CKCSEventSuggestionRejectedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.collaboration.CKCSEventCollaborationSessionRecoveredDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.collaboration.CKCSEventCollaborationSessionFinishedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.collaboration.CKCSEventUserDisconnectedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.comments.CKCSEventCommentAddedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.comments.CKCSEventCommentRemovedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.comments.CKCSEventCommentThreadRemovedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.comments.CKCSEventCommentThreadRestoredDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.comments.CKCSEventCommentThreadsRemovedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.comments.CKCSEventCommentUpdatedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.documentstorage.CKCSEventStorageDocumentRemovedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.documentstorage.CKCSEventStorageDocumentSaveFailedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.documentstorage.CKCSEventStorageDocumentSavedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.trackchanges.CKCSEventSuggestionAddedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.trackchanges.CKCSEventSuggestionRemovedDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.trackchanges.CKCSEventSuggestionRestoredDto;

import java.util.HashMap;
import java.util.Map;

public class CKCSEventDtoFactory {
    private static final Map<String, Class> eventClasses;

    static{
        eventClasses = new HashMap<String, Class>();
        // Ping Event
        eventClasses.put(CKCSEventPingDto.EVENT_KEY, CKCSEventPingDto.class);

        // Comments Events
        eventClasses.put(CKCSEventCommentAddedDto.EVENT_KEY, CKCSEventCommentAddedDto.class);
        eventClasses.put(CKCSEventCommentUpdatedDto.EVENT_KEY, CKCSEventCommentUpdatedDto.class);
        eventClasses.put(CKCSEventCommentRemovedDto.EVENT_KEY, CKCSEventCommentRemovedDto.class);
        eventClasses.put(CKCSEventCommentThreadRemovedDto.EVENT_KEY, CKCSEventCommentThreadRemovedDto.class);
        eventClasses.put(CKCSEventCommentThreadRestoredDto.EVENT_KEY, CKCSEventCommentThreadRestoredDto.class);
        eventClasses.put(CKCSEventCommentThreadsRemovedDto.EVENT_KEY, CKCSEventCommentThreadsRemovedDto.class);

        // Collaboration Events
        eventClasses.put(CKCSEventUserConnectedDto.EVENT_KEY, CKCSEventUserConnectedDto.class);
        eventClasses.put(CKCSEventUserDisconnectedDto.EVENT_KEY, CKCSEventUserDisconnectedDto.class);
        eventClasses.put(CKCSEventCollaborationSessionUpdatedDto.EVENT_KEY, CKCSEventCollaborationSessionUpdatedDto.class);
        eventClasses.put(CKCSEventCollaborationSessionFinishedDto.EVENT_KEY, CKCSEventCollaborationSessionFinishedDto.class);
        eventClasses.put(CKCSEventCollaborationSessionRecoveredDto.EVENT_KEY, CKCSEventCollaborationSessionRecoveredDto.class);

        // Document Storage Events
        eventClasses.put(CKCSEventStorageDocumentSavedDto.EVENT_KEY, CKCSEventStorageDocumentSavedDto.class);
        eventClasses.put(CKCSEventStorageDocumentSaveFailedDto.EVENT_KEY, CKCSEventStorageDocumentSaveFailedDto.class);
        eventClasses.put(CKCSEventStorageDocumentRemovedDto.EVENT_KEY, CKCSEventStorageDocumentRemovedDto.class);

        // Document Storage Events
        eventClasses.put(CKCSEventSuggestionAddedDto.EVENT_KEY, CKCSEventSuggestionAddedDto.class);
        eventClasses.put(CKCSEventSuggestionAcceptedDto.EVENT_KEY, CKCSEventSuggestionAcceptedDto.class);
        eventClasses.put(CKCSEventSuggestionRejectedDto.EVENT_KEY, CKCSEventSuggestionRejectedDto.class);
        eventClasses.put(CKCSEventSuggestionRemovedDto.EVENT_KEY, CKCSEventSuggestionRemovedDto.class);
        eventClasses.put(CKCSEventSuggestionRestoredDto.EVENT_KEY, CKCSEventSuggestionRestoredDto.class);
    }

    public static Class getCKCSEventDtoClass(String key){
        return eventClasses.get(key);
    }
}
