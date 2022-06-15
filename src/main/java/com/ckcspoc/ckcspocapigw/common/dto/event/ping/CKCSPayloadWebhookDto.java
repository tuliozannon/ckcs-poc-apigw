package com.ckcspoc.ckcspocapigw.common.dto.event.ping;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
public class CKCSPayloadWebhookDto {
    private String id;
    private String url;
}
