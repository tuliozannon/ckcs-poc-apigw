package com.ckcspoc.ckcspocapigw.util;

import com.ckcspoc.ckcspocapigw.dto.CKCSUserDto;
import com.ckcspoc.ckcspocapigw.dto.PersonShortenDto;
import org.springframework.stereotype.Component;

@Component
public class CKCSConverter {

    public CKCSUserDto fromPersonToCKCSUser(PersonShortenDto personDto, Integer personId){
        CKCSUserDto dto = new CKCSUserDto();
        dto.setId(personId);
        dto.setName(personDto.getFirstName()+" "+ personDto.getLastName());
        dto.setEmail(personDto.getEmail());
        dto.setAvatarUrl(personDto.getPhotoThumbnail());
        return dto;
    }


}
