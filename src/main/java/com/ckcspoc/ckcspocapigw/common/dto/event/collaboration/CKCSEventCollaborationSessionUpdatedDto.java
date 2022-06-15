package com.ckcspoc.ckcspocapigw.common.dto.event.collaboration;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
public class CKCSEventCollaborationSessionUpdatedDto {
    public static final String EVENT_KEY = "collaboration.document.updated";

    private CKCSPayloadDocumentUpdatedDto document;
}
