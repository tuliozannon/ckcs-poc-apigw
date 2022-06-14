package com.ckcspoc.ckcspocapigw.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CKCSResponseDto {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer status;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String message;

    public CKCSResponseDto(Integer status){
        this.status = status;
    }


}
