package com.biztrack.businessexpensetracker.repo;

import com.biztrack.businessexpensetracker.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepo extends JpaRepository<Request, Long> {
}
