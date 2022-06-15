package com.ckcspoc.ckcspocapigw.common.dto.event.documentstorage;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
public class CKCSPayloadFailDto {
    private String reason;
    private String details;
    private String trace_id;
}
