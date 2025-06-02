package com.biztrack.businessexpensetracker.dto.validation;

import jakarta.validation.constraints.NotNull;

public class ValRejectExpensesDTO {
    @NotNull(message = "Tidak Boleh Kosong")
    private Long id;

    @NotNull(message = "Komentar tidak boleh kosong")
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
