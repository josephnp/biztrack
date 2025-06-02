package com.biztrack.businessexpensetracker.repo;

import com.biztrack.businessexpensetracker.model.AccessMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessMenuRepo extends JpaRepository<AccessMenu, Long> {
}
