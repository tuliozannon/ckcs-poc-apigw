package com.ckcspoc.ckcspocapigw.common.service;

import com.ckcspoc.ckcspocapigw.common.config.CKCSAuthConfig;
import com.ckcspoc.ckcspocapigw.common.dto.CKCSUserDto;
import com.ckcspoc.ckcspocapigw.common.util.CKCSConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class CKCSAuthenticationService {
    private static final String HMAC_SHA256 = "HmacSHA256";
    private static final String POST_METHOD = "POST";
    private final CKCSAuthConfig ckcsAuthConfig;

    public CKCSAuthenticationService(
            CKCSAuthConfig ckcsAuthConfig){
        this.ckcsAuthConfig = ckcsAuthConfig;
    }

    // **************************************************************************************
    // Used by client CKEditor to authenticate Front-End user
    // **************************************************************************************
    public String getUserToken(CKCSUserDto userDto) {
        String environmentId = this.ckcsAuthConfig.getEnvironmentId();
        String accessKey = this.ckcsAuthConfig.getAccessKey();

        try {
            return this.createJwtToken(userDto, accessKey, environmentId);
        } catch (Exception exception) {
            return exception.toString();
        }
    }

    private String createJwtToken(CKCSUserDto userDto, String accessKey, String environmentId) throws UnsupportedEncodingException {
        Key key = Keys.hmacShaKeyFor(accessKey.getBytes("ASCII"));

        Map<String, Object> payload = new HashMap<String, Object>() {
            {
                put(CKCSConstants.TOKEN_AUD, environmentId);
                put(CKCSConstants.TOKEN_IAT, System.currentTimeMillis() / 1000);
                put(CKCSConstants.TOKEN_SUB, userDto.getId().toString());
                put(CKCSConstants.TOKEN_USER, new HashMap<String, Object>() {{
                    put(CKCSConstants.TOKEN_USER_EMAIL, userDto.getEmail());
                    put(CKCSConstants.TOKEN_USER_NAME, userDto.getName());
                    put(CKCSConstants.TOKEN_USER_AVATAR, userDto.getAvatarUrl());
                }});
                put(CKCSConstants.TOKEN_AUTH, new HashMap<String, Object>() {{
                    put(CKCSConstants.TOKEN_COLLABORATION, new HashMap<String, Object>() {{
                        put("", new HashMap<String, Object>() {{
                            put(CKCSConstants.TOKEN_ROLE, CKCSConstants.TOKEN_WRITER);
                        }});
                        put("", new HashMap<String, Object>() {{
                            put(CKCSConstants.TOKEN_ROLE, CKCSConstants.TOKEN_READER);
                        }});
                    }});
                }});
            }
        };

        return Jwts.builder().addClaims(payload).signWith(key).compact();
    }

    // **************************************************************************************
    // Used by server CKCloudServices S2S communications
    // **************************************************************************************
    public void validateSignature(String path, String ckcsSignature, String ckcsTimestamp, String body) throws Exception {
        String method = POST_METHOD;
        String generatedSignature = this.generateSignature(method, path, ckcsTimestamp, body);
        if (!ckcsSignature.equals(generatedSignature)){
            throw new Exception("Signature is not the same");
        }
    }

    public String generateSignature(String method, String path, String timestamp, String body) throws Exception {
        String methodUpperCase = method.toUpperCase();
        String apiSecret = this.ckcsAuthConfig.getApiSecret();

        String signatureData = methodUpperCase + path + timestamp;
        if (body != null) {
            signatureData += body;
        }

        return calculateHMACSHA256(signatureData, apiSecret);
    }

    private String calculateHMACSHA256(String data, String apiSecret) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(apiSecret.getBytes(), HMAC_SHA256);
        Mac mac = Mac.getInstance(HMAC_SHA256);

        mac.init(secretKeySpec);

        return toHexString(mac.doFinal(data.getBytes()));
    }

    private String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();

        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return formatter.toString();
    }




}
