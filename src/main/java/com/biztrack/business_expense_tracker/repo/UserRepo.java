package com.biztrack.business_expense_tracker.repo;

import com.biztrack.business_expense_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
