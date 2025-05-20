package com.biztrack.businessexpensetracker.repo;

import com.biztrack.businessexpensetracker.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<Department, Long> {
}
