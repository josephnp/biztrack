package com.biztrack.business_expense_tracker.repo;

import com.biztrack.business_expense_tracker.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepo extends JpaRepository<Status, Long> {
}
