package com.biztrack.businessexpensetracker.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResUserDTO {
    private Long id;
    @JsonProperty("employee-number")
    private String employeeNumber;
    @JsonProperty("full-name")
    private String fullName;
    private String email;
    private ResRoleDTO role;
    private ResDepartmentDTO department;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ResRoleDTO getRole() {
        return role;
    }

    public void setRole(ResRoleDTO role) {
        this.role = role;
    }

    public ResDepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(ResDepartmentDTO department) {
        this.department = department;
    }
}
