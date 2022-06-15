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
public class CKCSEventCommentThreadRemovedDto {
    public static final String EVENT_KEY = "ommentthread.removed";

    private CKCSPayloadDocumentDto document;
    private CKCSPayloadCommentThreadRemovedDto comment_thread;
}
