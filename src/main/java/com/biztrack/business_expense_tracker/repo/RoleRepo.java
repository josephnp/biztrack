package com.biztrack.business_expense_tracker.repo;

import com.biztrack.business_expense_tracker.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
}
