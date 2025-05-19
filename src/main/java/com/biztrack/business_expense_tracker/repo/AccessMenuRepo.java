package com.biztrack.business_expense_tracker.repo;

import com.biztrack.business_expense_tracker.model.AccessMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessMenuRepo extends JpaRepository<AccessMenu, Long> {
}
