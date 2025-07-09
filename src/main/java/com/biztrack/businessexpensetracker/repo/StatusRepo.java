package com.biztrack.businessexpensetracker.repo;

import com.biztrack.businessexpensetracker.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusRepo extends JpaRepository<Status, Long> {
    Page<Status> findByNameContainsIgnoreCase(String name, Pageable pageable);
    Page<Status> findByDescriptionContainsIgnoreCase(String description, Pageable pageable);

    List<Status> findByNameContainsIgnoreCase(String name);
}
