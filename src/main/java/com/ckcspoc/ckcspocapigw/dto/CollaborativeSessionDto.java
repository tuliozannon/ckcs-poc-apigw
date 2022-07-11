package com.ckcspoc.ckcspocapigw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollaborativeSessionDto {
    private String documentId;
    private String bundleVersionId;
    private String data;
    private Boolean useInitialDate = Boolean.FALSE;
}
