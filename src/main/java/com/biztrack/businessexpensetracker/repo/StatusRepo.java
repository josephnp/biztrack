package com.biztrack.businessexpensetracker.repo;

import com.biztrack.businessexpensetracker.model.Status;
import com.biztrack.businessexpensetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StatusRepo extends JpaRepository<Status, Long> {
    Optional<Status> findByName(String name);
}
