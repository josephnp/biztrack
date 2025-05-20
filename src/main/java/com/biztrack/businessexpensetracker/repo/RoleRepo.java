package com.biztrack.businessexpensetracker.repo;

import com.biztrack.businessexpensetracker.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
}
