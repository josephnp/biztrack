package com.biztrack.businessexpensetracker.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResRoleDTO {
    private Long id;
    private String name;
    private String description;

    @JsonProperty("list-menu")
    private List<ResMenuDTO> listMenu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<ResMenuDTO> getListMenu() {
        return listMenu;
    }

    public void setListMenu(List<ResMenuDTO> listMenu) {
        this.listMenu = listMenu;
    }
}
