package com.biztrack.businessexpensetracker.repo;

import com.biztrack.businessexpensetracker.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepo extends JpaRepository<Status, Long> {
}
