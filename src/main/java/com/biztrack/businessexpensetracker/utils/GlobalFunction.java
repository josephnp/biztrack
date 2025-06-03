package com.biztrack.businessexpensetracker.utils;

import com.biztrack.businessexpensetracker.config.JwtConfig;
import com.biztrack.businessexpensetracker.config.OtherConfig;
import com.biztrack.businessexpensetracker.security.Crypto;
import com.biztrack.businessexpensetracker.security.JwtUtility;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;


@Component
public class GlobalFunction {

    public static Map<String, Object> extractToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.substring(7);
        if (JwtConfig.getTokenEncryptEnable().equals("y")) {
            token = Crypto.performDecrypt(token);
        }
        return new JwtUtility().mappingBodyToken(token);
    }
}