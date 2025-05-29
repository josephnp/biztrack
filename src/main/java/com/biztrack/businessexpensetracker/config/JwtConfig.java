package com.biztrack.businessexpensetracker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:jwt.properties")
public class JwtConfig {
    private static String secretKey;
    private static Long timeExpiration;
    private static String tokenEncryptEnable;

    public static String getSecretKey() {
        return secretKey;
    }

    @Value("${secret.key}")
    public void setSecretKey(String secretKey) {
        JwtConfig.secretKey = secretKey;
    }

    public static Long getTimeExpiration() {
        return timeExpiration;
    }

    @Value("${time.expiration}")
    public void setTimeExpiration(Long timeExpiration) {
        JwtConfig.timeExpiration = timeExpiration;
    }

    public static String getTokenEncryptEnable() {
        return tokenEncryptEnable;
    }

    @Value("${token.enable.encrypt}")
    public void setTokenEncryptEnable(String tokenEncryptEnable) {
        JwtConfig.tokenEncryptEnable = tokenEncryptEnable;
    }
}
