package com.ckcspoc.ckcspocapigw.common.dto.event.trackchanges;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
public class CKCSPayloadSuggestionRemovedDto {
    private String id;
    private Instant removed_at;
}