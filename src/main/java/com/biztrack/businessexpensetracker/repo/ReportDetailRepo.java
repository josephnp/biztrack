package com.biztrack.businessexpensetracker.repo;

import com.biztrack.businessexpensetracker.model.ReportDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportDetailRepo extends JpaRepository<ReportDetail, Long> {
}
