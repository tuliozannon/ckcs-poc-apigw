package com.ckcspoc.ckcspocapigw.common.dto.event.ping;

import com.ckcspoc.ckcspocapigw.common.dto.event.CKCSEventDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
public class CKCSEventPingDto {
    public static final String EVENT_KEY = "ping";

    private CKCSPayloadWebhookDto webhook;
}
