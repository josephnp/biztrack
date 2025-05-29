package com.biztrack.businessexpensetracker.repo;

import com.biztrack.businessexpensetracker.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepo extends JpaRepository<Department, Long> {

    Optional<Department> findById(Long id);
    Optional<Department> findByName(String name);
}
