package com.ckcspoc.ckcspocapigw.common.dto.event.documentstorage;

import com.ckcspoc.ckcspocapigw.common.dto.event.CKCSPayloadDocumentDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
public class CKCSPayloadDocumentSaveFailedDto extends CKCSPayloadDocumentDto {
    private Instant failed_at;
}
