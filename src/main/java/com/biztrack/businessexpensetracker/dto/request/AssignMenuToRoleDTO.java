package com.biztrack.businessexpensetracker.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AssignMenuToRoleDTO {
    @JsonProperty("role-id")
    private Long roleId;

    @JsonProperty("menu-id")
    private List<Long> menuId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<Long> getMenuId() {
        return menuId;
    }

    public void setMenuId(List<Long> menuId) {
        this.menuId = menuId;
    }
}
