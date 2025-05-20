package com.biztrack.businessexpensetracker.dto.validation;

import jakarta.validation.constraints.Pattern;


public class LoginDTO {

    @Pattern(regexp = "", message = "")
    private String email;

    @Pattern(regexp = "", message = "")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
