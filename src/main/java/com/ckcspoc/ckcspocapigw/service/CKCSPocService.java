package com.ckcspoc.ckcspocapigw.service;

import com.ckcspoc.ckcspocapigw.dto.CKCSUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CKCSPocService {
    private final CKCSAuthenticationService ckcsAuthenticationService;

    @Autowired
    public CKCSPocService(CKCSAuthenticationService ckcsAuthenticationService) {
        this.ckcsAuthenticationService = ckcsAuthenticationService;
    }

    public String getToken(Integer userId, String env) {
        CKCSUserDto dto = this.ckcsAuthenticationService.getCKCSUserDtoById(userId);
        String jwtToken = this.ckcsAuthenticationService.getTokenByUserAndEnv(dto, env);
        return jwtToken;
    }



}
