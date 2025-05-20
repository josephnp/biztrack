package com.biztrack.business_expense_tracker.core;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IService<T> {
    public ResponseEntity<Object> save(T t, HttpServletRequest request);

    public ResponseEntity<Object> update(Long id, T t, HttpServletRequest request);

    public ResponseEntity<Object> delete(Long id, HttpServletRequest request);

    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request);
}
