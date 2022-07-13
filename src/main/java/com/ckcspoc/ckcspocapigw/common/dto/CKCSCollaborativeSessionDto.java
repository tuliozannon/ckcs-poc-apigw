package com.ckcspoc.ckcspocapigw.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CKCSCollaborativeSessionDto {
    private String document_id;
    private String bundle_version;
    private String data;
    private Boolean use_initial_data = Boolean.FALSE;
}
