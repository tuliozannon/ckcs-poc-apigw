package com.ckcspoc.ckcspocapigw.service;

import com.ckcspoc.ckcspocapigw.dto.CKCSUserDto;
import com.ckcspoc.ckcspocapigw.util.CKCSConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class CKCSAuthenticationService {
    public CKCSUserDto getCKCSUserDtoById(Integer userId){
        String[] avatarUrls = {
                "https://gravatar.com/avatar/7f956b5e39bbb1074237a47aaf8f573a?s=400&d=robohash&r=x",
                "https://gravatar.com/avatar/396a0bfb1f14964267573fb479db98a8?s=400&d=robohash&r=x",
                "https://gravatar.com/avatar/b074ab9ed9815dd593c0cf1112bac7be?s=400&d=robohash&r=x",
                "https://gravatar.com/avatar/c149d50937bd9bbbbdd821d3bc4bff6c?s=400&d=robohash&r=x",
                "https://gravatar.com/avatar/4846c68af9c155566a9ad5da35a99335?s=400&d=robohash&r=x"
        };
        CKCSUserDto dto = new CKCSUserDto();
        if ((userId==null)||(userId>4)){
            userId=5;
        }
        dto.setId(userId);
        dto.setName("User "+userId+" (CKCS Poc)");
        dto.setEmail("ckcspoc.user"+userId+"@gmail.com");
        dto.setAvatarUrl(avatarUrls[userId-1]);
        return dto;
    }

    public String getTokenByUserAndEnv(CKCSUserDto user, String env) {
        String environmentId = CKCSConfig.getEnvironmentIdByEnv(env);
        String accessKey = CKCSConfig.getAccessKeyByEnv(env);

        try {
            return this.createJwtToken(user, accessKey, environmentId);
        } catch (Exception exception) {
            return exception.toString();
        }
    }

    private String createJwtToken(CKCSUserDto user, String accessKey, String environmentId) throws UnsupportedEncodingException {
        Key key = Keys.hmacShaKeyFor(accessKey.getBytes("ASCII"));

        Map<String, Object> payload = new HashMap<String, Object>() {{
            put("aud", environmentId);
            put("iat", System.currentTimeMillis() / 1000);
            put("sub", user.getId().toString());
            put("user", new HashMap<String, Object>() {{
                put("email", user.getEmail());
                put("name", user.getName());
                put("avatar", user.getAvatarUrl());
            }});
            put("auth", new HashMap<String, Object>() {{
                put("collaboration", new HashMap<String, Object>() {{
                    put("", new HashMap<String, Object>() {{
                        put("role", "writer");
                    }});
                    put("", new HashMap<String, Object>() {{
                        put("role", "reader");
                    }});
                }});
            }});
        }};

        return Jwts.builder().addClaims(payload).signWith(key).compact();
    }

}

soap 130, personId =1000, medicalnotes
        t100-3423742328

Tuilio medico do soap pode confirmar tudo

William medico do soap pode confirmar tudo
        Enfermeiros podem ler
        Enfermeiros comments

medical notes - medicos fazem o q quiser
discharde notes - faz tudo
exam notes - Perfil X
