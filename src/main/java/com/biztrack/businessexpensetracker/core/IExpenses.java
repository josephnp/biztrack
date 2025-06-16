package com.biztrack.businessexpensetracker.core;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface IExpenses{
    public ResponseEntity<Object> cancel(Long id, HttpServletRequest request);
    public ResponseEntity<Object> approve(Long id, HttpServletRequest request);
    public ResponseEntity<Object> reject(Long id, String comment, HttpServletRequest request);
}
