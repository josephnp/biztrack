package com.biztrack.businessexpensetracker.controller;

import com.biztrack.businessexpensetracker.config.OtherConfig;
import com.biztrack.businessexpensetracker.dto.validation.ValStatusDTO;
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
@RequestMapping("status")
public class StatusController {
    @Autowired
    private StatusService statusService;

    @PostMapping
    @PreAuthorize("hasAuthority('Status')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValStatusDTO valStatusDTO,
                                       HttpServletRequest request){
        return statusService.save(statusService.mapToStatus(valStatusDTO),request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Status')")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody ValStatusDTO valStatusDTO,
                                         HttpServletRequest request){
        return statusService.update(id,statusService.mapToStatus(valStatusDTO),request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Status')")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request){
        return statusService.delete(id,request);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('Status')")
    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0, OtherConfig.getDefaultPaginationSize(), Sort.by("id"));
        return statusService.findAll(pageable,request);
    }

    @GetMapping("/{sort}/{sort-by}/{page}")
    @PreAuthorize("hasAuthority('Status')")
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
        return statusService.findByParam(pageable,column,value,request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Status')")
    public ResponseEntity<Object> findById(
            @PathVariable Long id,
            HttpServletRequest request){
        return statusService.findById(id,request);
    }
}
