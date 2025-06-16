package com.biztrack.businessexpensetracker.repo;

import com.biztrack.businessexpensetracker.model.RequestDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestDetailRepo extends JpaRepository<RequestDetail, Long> {
}
