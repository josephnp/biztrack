package com.biztrack.businessexpensetracker.repo;

import com.biztrack.businessexpensetracker.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuRepo extends JpaRepository<Menu, Long> {
    Optional<Menu> findByName(String name);
    Optional<Menu> findById(Long id);
}
