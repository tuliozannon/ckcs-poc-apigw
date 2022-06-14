package com.ckcspoc.ckcspocapigw.service;


import com.ckcspoc.ckcspocapigw.dto.PersonShortenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
//TO be used by real system
public class PersonService {
    public PersonShortenDto getShortenWithRoles(Integer id) {
        String[] avatarUrls = {
                "https://gravatar.com/avatar/7f956b5e39bbb1074237a47aaf8f573a?s=400&d=robohash&r=x",
                "https://gravatar.com/avatar/396a0bfb1f14964267573fb479db98a8?s=400&d=robohash&r=x",
                "https://gravatar.com/avatar/b074ab9ed9815dd593c0cf1112bac7be?s=400&d=robohash&r=x",
                "https://gravatar.com/avatar/c149d50937bd9bbbbdd821d3bc4bff6c?s=400&d=robohash&r=x",
                "https://gravatar.com/avatar/4846c68af9c155566a9ad5da35a99335?s=400&d=robohash&r=x"
        };
        PersonShortenDto dto = new PersonShortenDto();
        if ((id==null)||(id>4)){
            id=5;
        }
        dto.setFirstName("User "+id);
        dto.setLastName("(CKCS Poc)");
        dto.setEmail("ckeditor"+id+"@gmail.com");
        dto.setPhotoThumbnail(avatarUrls[id-1]);
        return dto;
    }
}
