package com.biztrack.business_expense_tracker.dto.validation;

import com.biztrack.business_expense_tracker.model.Department;
import com.biztrack.business_expense_tracker.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class ValAddUserDTO {

    @NotBlank(message = "Name can't be blank")
    @NotEmpty(message = "Name can't be empty")
    private String name;

    @NotBlank(message = "Email can't be blank")
    @NotEmpty(message = "Email can't be empty")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.com$", message = "Email format is not valid")
    private String email;

    private String employeeNumber;

    private String password;

    private Department department;

    private Role role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
