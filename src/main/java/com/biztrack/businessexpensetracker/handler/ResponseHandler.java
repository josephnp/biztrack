package com.biztrack.businessexpensetracker.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Response Body untuk masalah Bisnis Logic dan Status Berhasil
 */
public class ResponseHandler {

    public ResponseEntity<Object> handleResponse(
            String message,
            HttpStatus status,
            Object data,
            Object errorCode,
            HttpServletRequest request
    ) {

        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("data", data == null ? "" : data);
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("success", !status.isError());
        if (errorCode != null) {
            response.put("error-code", errorCode);
            response.put("path", request.getRequestURI());
        }
        return new ResponseEntity<>(response, status);
    }

    public ResponseEntity<Object> handleResponse(
            String message,
            HttpStatus status,
            Object data,
            Object errorCode,
            WebRequest request
    ) {

        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("data", data == null ? "" : data);
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("success", !status.isError());
        if (errorCode != null) {
            response.put("error-code", errorCode);
            response.put("path", request.getContextPath());
        }
        return new ResponseEntity<>(response, status);
    }
}
