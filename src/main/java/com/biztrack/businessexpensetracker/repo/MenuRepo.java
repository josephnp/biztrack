package com.biztrack.businessexpensetracker.repo;

import com.biztrack.businessexpensetracker.model.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepo extends JpaRepository<Menu, Long> {

    Page<Menu> findByNameContainsIgnoreCase(String name, Pageable pageable);
    Page<Menu> findByDescriptionContainsIgnoreCase(String description, Pageable pageable);

    List<Menu> findByNameContainsIgnoreCase(String name);
}
