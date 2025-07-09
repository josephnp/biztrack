package com.biztrack.businessexpensetracker.dto.validation;

import com.biztrack.businessexpensetracker.dto.rel.RelDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ValRoleDTO {
    @NotEmpty(message = "Name can't be empty")
    private String name;

    @NotEmpty(message = "Description can't be empty")
    private String description;

    @NotNull(message = "Relasi Tidak Boleh Kosong")
    @JsonProperty("list-menu")
    private List<RelDTO> listMenu;

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

    public List<RelDTO> getListMenu() {
        return listMenu;
    }

    public void setListMenu(List<RelDTO> listMenu) {
        this.listMenu = listMenu;
    }
}
