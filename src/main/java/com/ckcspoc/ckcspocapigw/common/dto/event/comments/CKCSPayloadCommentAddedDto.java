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
public class CKCSPayloadCommentAddedDto {
    private String id;
    private Instant created_at;
    private String content;
    private Map attributes;
    private String thread_id;
    private CKCSPayloadUserDto user;
}
