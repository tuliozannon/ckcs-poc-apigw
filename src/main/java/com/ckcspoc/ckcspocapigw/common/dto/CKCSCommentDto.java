package com.ckcspoc.ckcspocapigw.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CKCSCommentDto {
    private String id;
    private String document_id;
    private String thread_id;
    private String content;
    private Object attributes;
    private Object user;
    private Instant created_at;
    private Instant deleted_at;
}
