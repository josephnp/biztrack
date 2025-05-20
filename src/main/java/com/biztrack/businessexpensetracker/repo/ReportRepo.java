package com.biztrack.businessexpensetracker.repo;

import com.biztrack.businessexpensetracker.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepo extends JpaRepository<Report, Long> {
}
