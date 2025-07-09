package com.biztrack.businessexpensetracker.repo;

import com.biztrack.businessexpensetracker.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepo extends JpaRepository<Department, Long> {

    Page<Department> findByNameContainsIgnoreCase(String name, Pageable pageable);
    Page<Department> findByDescriptionContainsIgnoreCase(String description, Pageable pageable);

    List<Department> findByNameContainsIgnoreCase(String name);
}
