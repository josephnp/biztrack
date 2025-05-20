package com.biztrack.business_expense_tracker.dto.validation;

import jakarta.validation.constraints.Pattern;
import org.antlr.v4.runtime.misc.NotNull;


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
