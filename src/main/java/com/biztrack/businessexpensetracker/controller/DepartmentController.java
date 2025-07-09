package com.biztrack.businessexpensetracker.controller;

import com.biztrack.businessexpensetracker.config.OtherConfig;
import com.biztrack.businessexpensetracker.dto.validation.ValDepartmentDTO;
import com.biztrack.businessexpensetracker.service.DepartmentService;
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
@RequestMapping("/dept")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    @PreAuthorize("hasAuthority('Department')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValDepartmentDTO valDepartmentDTO,
                                       HttpServletRequest request){
        return departmentService.save(departmentService.mapToDepartment(valDepartmentDTO),request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Department')")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody ValDepartmentDTO valDepartmentDTO,
                                         HttpServletRequest request){
        return departmentService.update(id,departmentService.mapToDepartment(valDepartmentDTO),request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Department')")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request){
        return departmentService.delete(id,request);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('Department')")
    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0, OtherConfig.getDefaultPaginationSize(), Sort.by("id"));
        return departmentService.findAll(pageable,request);
    }

    @GetMapping("/{sort}/{sort-by}/{page}")
    @PreAuthorize("hasAuthority('Department')")
    public ResponseEntity<Object> findByParam(
            @PathVariable String sort,
            @PathVariable(value = "sort-by") String sortBy,
            @PathVariable Integer page,
            @RequestParam Integer size,
            @RequestParam String column,
            @RequestParam String value,
            HttpServletRequest request){
        Pageable pageable;
        if (sort.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy));
        }
        return departmentService.findByParam(pageable,column,value,request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Department')")
    public ResponseEntity<Object> findById(
            @PathVariable Long id,
            HttpServletRequest request){
        return departmentService.findById(id,request);
    }
}
