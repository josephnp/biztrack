package com.biztrack.businessexpensetracker.dto.report;

import com.biztrack.businessexpensetracker.model.Department;
import com.biztrack.businessexpensetracker.model.Role;
import jakarta.persistence.*;

import java.util.UUID;

public class RepUserDTO {
    private UUID id;

    private Role role;

    private String employeeNumber;

    private String fullName;

    private String email;

    private Department department;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

}
