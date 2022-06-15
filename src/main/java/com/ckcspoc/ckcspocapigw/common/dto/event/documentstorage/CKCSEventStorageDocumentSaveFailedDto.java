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
public class CKCSEventStorageDocumentSaveFailedDto {
    public static final String EVENT_KEY = "storage.document.save.failed";

    private CKCSPayloadDocumentSaveFailedDto document;
    private CKCSPayloadEditorBundleDto editor;
    private CKCSPayloadFailDto fail;
}

