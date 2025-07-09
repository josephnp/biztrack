package com.biztrack.businessexpensetracker.repo;

import com.biztrack.businessexpensetracker.model.Department;
import com.biztrack.businessexpensetracker.model.Role;
import com.biztrack.businessexpensetracker.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Page<Role> findByNameContainsIgnoreCase(String name, Pageable pageable);
    Page<Role> findByDescriptionContainsIgnoreCase(String description, Pageable pageable);
    Page<Role> findByListMenu_NameContainsIgnoreCase(String description, Pageable pageable);

    List<Role> findByNameContainsIgnoreCase(String name);
}
