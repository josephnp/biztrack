package com.biztrack.businessexpensetracker.dto.report;

import com.biztrack.businessexpensetracker.dto.MenuDTO;

import java.util.List;

public class RepRoleDTO {
    private Long id;
    private String name;
    private String description;
    private List<MenuDTO> menus;

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

    public List<MenuDTO> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuDTO> menus) {
        this.menus = menus;
    }
}
