package com.ckcspoc.ckcspocapigw.dto;

import lombok.Data;

@Data
public class PersonShortenDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String photoThumbnail;
}
