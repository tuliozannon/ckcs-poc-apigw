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
public class CKCSEventCommentAddedDto {
    public static final String EVENT_KEY = "comment.added";

    private CKCSPayloadDocumentDto document;
    private CKCSPayloadCommentAddedDto comment;
}
