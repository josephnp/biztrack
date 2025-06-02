package com.biztrack.businessexpensetracker.controller;

import com.biztrack.businessexpensetracker.config.OtherConfig;
import com.biztrack.businessexpensetracker.dto.request.AssignMenuToRoleDTO;
import com.biztrack.businessexpensetracker.dto.validation.ValRoleDTO;
import com.biztrack.businessexpensetracker.dto.validation.ValStatusDTO;
import com.biztrack.businessexpensetracker.service.RoleService;
import com.biztrack.businessexpensetracker.service.StatusService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValRoleDTO valRoleDTO,
                                       HttpServletRequest request){
        return roleService.save(roleService.mapToRole(valRoleDTO),request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody ValRoleDTO valRoleDTO,
                                         HttpServletRequest request){
        return roleService.update(id,roleService.mapToRole(valRoleDTO),request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request){
        return roleService.delete(id,request);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0, OtherConfig.getDefaultPaginationSize(), Sort.by("id"));
        return roleService.findAll(pageable,request);
    }

    @GetMapping("/{roleId}/menus")
    public ResponseEntity<Object> getMenusByRoleId( @PathVariable Long roleId,
                                                    Pageable pageable,
                                                    HttpServletRequest request){
        return roleService.getMenusByRoleId(roleId, pageable, request);
    }

    @PostMapping("/assign-menus")
    public ResponseEntity<Object> assignMenusToRole(
            @RequestBody AssignMenuToRoleDTO assignMenuToRoleDTO,
            HttpServletRequest request
    ) {
        return roleService.assignMenusToRole(assignMenuToRoleDTO, request);
    }
}
