package com.ckcspoc.ckcspocapigw.controller;

import com.ckcspoc.ckcspocapigw.service.CKCSPocService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("ckcspoc")
public class CKCSPocController {

    private final CKCSPocService ckcsPocService;

    @Autowired
    public CKCSPocController(CKCSPocService ckcsPocService) {
        this.ckcsPocService = ckcsPocService;
    }

    @GetMapping("/token")
    public String getToken(@RequestParam(value = "userId") Integer userId,
                           @RequestParam(value = "env", defaultValue = "dev") String env) {
        return this.ckcsPocService.getToken(userId, env);
    }
}
