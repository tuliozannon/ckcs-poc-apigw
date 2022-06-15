package com.ckcspoc.ckcspocapigw.common.dto.event.comments;

import com.ckcspoc.ckcspocapigw.common.dto.event.CKCSPayloadDocumentDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
public class CKCSEventCommentThreadRestoredDto {
    public static final String EVENT_KEY = "commentthread.restored";

    private CKCSPayloadDocumentDto document;
    private CKCSPayloadCommentThreadRestoredDto comment_thread;
}
