package com.ckcspoc.ckcspocapigw.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CKCSUserDto {
    private Integer id;
    private String name;
    private String email;
    private String avatarUrl;
}
