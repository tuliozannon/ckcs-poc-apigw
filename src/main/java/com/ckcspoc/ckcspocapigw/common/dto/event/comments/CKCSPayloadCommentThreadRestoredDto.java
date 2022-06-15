package com.ckcspoc.ckcspocapigw.common.dto.event.comments;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
public class CKCSPayloadCommentThreadRestoredDto {
    private String id;
    private Instant restored_at;
    private List<Map> comments;
}
