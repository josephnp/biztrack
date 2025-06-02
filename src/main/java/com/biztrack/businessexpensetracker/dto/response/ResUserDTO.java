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
    private ResDepartmentDTO departement;
}
