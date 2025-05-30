package com.biztrack.businessexpensetracker.dto.validation;

import com.biztrack.businessexpensetracker.model.Department;
import com.biztrack.businessexpensetracker.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class ValUserDTO {
    @NotBlank(message = "Name can't be blank")
    @NotEmpty(message = "Name can't be empty")
    private String fullName;

    @NotBlank(message = "Email can't be blank")
    @NotEmpty(message = "Email can't be empty")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.com$", message = "Email format is not valid")
    private String email;

    // employee number example: EMP0001
    @Pattern(regexp = "^EMP[0-9]{4}$", message = "Employee Number is not  valid")
    private String employeeNumber;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@_#\\-$])[\\w].{8,15}$",
            message = "Password format is not valid")
    private String password;

    private Department department;

    private Role role;

    private boolean isActive = true;

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

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
