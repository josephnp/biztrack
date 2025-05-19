package com.biztrack.business_expense_tracker.repo;

import com.biztrack.business_expense_tracker.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<Department, Long> {
}
