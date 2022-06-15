package com.ckcspoc.ckcspocapigw.common.dto.event.collaboration;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
public class CKCSEventCollaborationSessionRecoveredDto {
    public static final String EVENT_KEY = "collaboration.document.recovered";

    private CKCSPayloadDocumentRecoveredDto document;
}
