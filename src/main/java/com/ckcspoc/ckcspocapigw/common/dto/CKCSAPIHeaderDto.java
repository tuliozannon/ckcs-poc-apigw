package com.ckcspoc.ckcspocapigw.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CKCSAPIHeaderDto {
    private String path;
    private String method;
    private String timestamp;
    private String signature;
    private String body;
}
