package com.ckcspoc.ckcspocapigw.common.dto.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
@EqualsAndHashCode(callSuper=true)
public class CKCSPayloadDocumentRemovedDto extends CKCSPayloadDocumentDto {
    private Instant removed_at;
    private String data;
}
