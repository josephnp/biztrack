package com.biztrack.businessexpensetracker.dto.validation;

import jakarta.validation.constraints.NotEmpty;

public class ValRoleDTO {

    @NotEmpty(message = "Name can't be empty")
    private String name;

    @NotEmpty(message = "Name can't be empty")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
