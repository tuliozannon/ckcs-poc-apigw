package com.ckcspoc.ckcspocapigw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDto {
    private Integer id;
    private String channelId;
    private String content;
}
