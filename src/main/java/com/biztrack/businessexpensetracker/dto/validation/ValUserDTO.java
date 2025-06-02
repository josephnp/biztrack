package com.biztrack.businessexpensetracker.dto.validation;

import com.biztrack.businessexpensetracker.dto.rel.RelDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ValUserDTO {

    @NotBlank(message = "Name can't be blank")
    @NotEmpty(message = "Name can't be empty")
    @JsonProperty("full-name")
    private String fullName;

    @NotBlank(message = "Email can't be blank")
    @NotEmpty(message = "Email can't be empty")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.com$", message = "Email format is not valid")
    private String email;

    // employee number example: EMP0001
    @Pattern(regexp = "^EMP[0-9]{4}$", message = "Employee Number is not  valid")
    @JsonProperty("employee-number")
    private String employeeNumber;

    @NotNull(message = "Relasi Tidak Boleh Kosong")
    private RelDTO department;

    @NotNull(message = "Relasi Tidak Boleh Kosong")
    private RelDTO role;


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

    public RelDTO getDepartment() {
        return department;
    }

    public void setDepartment(RelDTO department) {
        this.department = department;
    }

    public RelDTO getRole() {
        return role;
    }

    public void setRole(RelDTO role) {
        this.role = role;
    }

}
