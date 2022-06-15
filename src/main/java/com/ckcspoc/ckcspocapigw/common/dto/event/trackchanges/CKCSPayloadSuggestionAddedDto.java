package com.ckcspoc.ckcspocapigw.common.dto.event.trackchanges;

import com.ckcspoc.ckcspocapigw.common.dto.event.CKCSPayloadUserDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
public class CKCSPayloadSuggestionAddedDto {
    private String id;
    private Instant created_at;
    private String child_of;
    private CKCSPayloadUserDto user;
}
