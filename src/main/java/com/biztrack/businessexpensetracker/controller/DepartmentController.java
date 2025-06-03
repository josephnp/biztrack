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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dept")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody ValDepartmentDTO valDepartmentDTO,
                                       HttpServletRequest request){
        return departmentService.save(departmentService.mapToDepartment(valDepartmentDTO),request);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody ValDepartmentDTO valDepartmentDTO,
                                         HttpServletRequest request){
        return departmentService.update(id,departmentService.mapToDepartment(valDepartmentDTO),request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request){
        return departmentService.delete(id,request);
    }

    @GetMapping
    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0, OtherConfig.getDefaultPaginationSize(), Sort.by("id"));
        return departmentService.findAll(pageable,request);
    }


}
