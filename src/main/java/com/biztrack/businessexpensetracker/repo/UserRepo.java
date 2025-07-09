package com.biztrack.businessexpensetracker.repo;

import com.biztrack.businessexpensetracker.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Page<User> findByFullNameContainsIgnoreCase(String name, Pageable pageable);
    Page<User> findByEmailContainsIgnoreCase(String email, Pageable pageable);
    Page<User> findByEmployeeNumberContainsIgnoreCase(String employeeNumber, Pageable pageable);

    Page<User> findByRole_NameContainsIgnoreCase(String role, Pageable pageable);

    Optional<User> findByEmailContainsIgnoreCase(String employeeNumber);
}
