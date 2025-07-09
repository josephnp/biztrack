package com.biztrack.businessexpensetracker.repo;

import com.biztrack.businessexpensetracker.model.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepo extends JpaRepository<Request, Long> {
    Page<Request> findByPurposeContainsIgnoreCase(String purpose, Pageable pageable);
    Page<Request> findByDescriptionContainsIgnoreCase(String description, Pageable pageable);

    Page<Request> findByUser_FullNameContainsIgnoreCase(String name, Pageable pageable);
    Page<Request> findByStatus_NameContainsIgnoreCase(String status, Pageable pageable);
}
