package com.biztrack.businessexpensetracker.repo;

import com.biztrack.businessexpensetracker.model.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepo extends JpaRepository<Report, Long> {
    Page<Report> findByRequest_PurposeContainsIgnoreCase(String purpose, Pageable pageable);
    Page<Report> findByRequest_DescriptionContainsIgnoreCase(String description, Pageable pageable);
    Page<Report> findByRequest_User_FullNameContainsIgnoreCase(String name, Pageable pageable);
    Page<Report> findByStatus_NameContainsIgnoreCase(String status, Pageable pageable);
}
