package com.ckcspoc.ckcspocapigw.common.dto.event.comments;

import com.ckcspoc.ckcspocapigw.common.dto.event.CKCSPayloadDocumentDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
public class CKCSEventCommentThreadsRemovedDto {
    public static final String EVENT_KEY = "commentthread.all.removed";

    private CKCSPayloadDocumentDto document;
    private List<CKCSPayloadCommentThreadRemovedDto> comment_threads;
}
