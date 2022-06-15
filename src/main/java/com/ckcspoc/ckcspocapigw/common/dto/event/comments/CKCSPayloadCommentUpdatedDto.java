package com.ckcspoc.ckcspocapigw.common.dto.event.comments;

import com.ckcspoc.ckcspocapigw.common.dto.event.CKCSPayloadUserDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
public class CKCSPayloadCommentUpdatedDto {
    private String id;
    private Instant updated_at;
    private String content;
    private String old_content;
    private Map attributes;
    private Map old_attributes;
    private String thread_id;
    private CKCSPayloadUserDto user;
}
