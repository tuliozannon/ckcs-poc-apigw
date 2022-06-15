package com.ckcspoc.ckcspocapigw.common.dto.event.documentstorage;

import com.ckcspoc.ckcspocapigw.common.dto.event.CKCSEventDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
public class CKCSEventStorageDocumentSavedDto {
    public static final String EVENT_KEY = "storage.document.saved";

    private CKCSPayloadDocumentSavedDto document;
}
