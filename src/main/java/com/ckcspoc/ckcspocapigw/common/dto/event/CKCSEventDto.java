package com.ckcspoc.ckcspocapigw.common.dto.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
public class CKCSEventDto {
    private String event;
    private String environment_id;
    private Instant sent_at;
    private Object payload;
}
