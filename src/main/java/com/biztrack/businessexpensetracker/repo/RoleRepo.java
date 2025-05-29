package com.biztrack.businessexpensetracker.repo;

import com.biztrack.businessexpensetracker.model.Role;
import com.biztrack.businessexpensetracker.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
