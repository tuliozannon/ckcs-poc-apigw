package com.ckcspoc.ckcspocapigw.common.dto.event.trackchanges;

import com.ckcspoc.ckcspocapigw.common.dto.event.CKCSPayloadDocumentDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
public class CKCSEventSuggestionRejectedDto {
    public static final String EVENT_KEY = "suggestion.rejected";

    private CKCSPayloadDocumentDto document;
    private CKCSPayloadSuggestionRejectedDto suggestion;
}
