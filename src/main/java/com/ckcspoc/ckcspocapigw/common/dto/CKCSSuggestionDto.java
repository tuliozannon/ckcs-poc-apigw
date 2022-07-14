package com.ckcspoc.ckcspocapigw.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CKCSSuggestionDto {
    private String id;
    private String document_id;
    private String author_id;
    private String type;
    private Object data;
    private Object attributes;
    private Instant created_at;
    private Instant deleted_at;
    private Boolean has_comments;
    private String original_suggestion_id;
    private String state;
    private String requester_id;
}
