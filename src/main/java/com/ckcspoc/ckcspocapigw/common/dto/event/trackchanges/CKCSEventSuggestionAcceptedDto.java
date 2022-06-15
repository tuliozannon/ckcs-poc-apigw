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
public class CKCSEventSuggestionAcceptedDto {
    public static final String EVENT_KEY = "suggestion.accepted";

    private CKCSPayloadDocumentDto document;
    private CKCSPayloadSuggestionAcceptedDto suggestion;
}
