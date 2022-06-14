package com.ckcspoc.ckcspocapigw.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ckcs.auth")
@Data
public class CKCSAuthConfig {
    private String environmentId;
    private String apiSecret;
    private String accessKey;
}
