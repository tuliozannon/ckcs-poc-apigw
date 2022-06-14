package com.ckcspoc.ckcspocapigw.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
public class CKCSWebhookEventDto {
    private String event;
    private String environment_id;
    private Instant sent_at;
    private Object payload;
}
