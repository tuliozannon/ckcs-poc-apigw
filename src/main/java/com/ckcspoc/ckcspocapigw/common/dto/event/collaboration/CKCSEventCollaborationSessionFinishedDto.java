package com.ckcspoc.ckcspocapigw.common.dto.event.collaboration;

import com.ckcspoc.ckcspocapigw.common.dto.event.CKCSPayloadDocumentRemovedDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
public class CKCSEventCollaborationSessionFinishedDto {
    public static final String EVENT_KEY = "collaboration.document.finished";

    private CKCSPayloadDocumentRemovedDto document;
}
