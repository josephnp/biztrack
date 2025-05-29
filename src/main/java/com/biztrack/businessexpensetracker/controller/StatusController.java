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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/status")
public class StatusController {
    @Autowired
    private StatusService statusService;

    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody ValStatusDTO valStatusDTO,
                                       HttpServletRequest request){
        return statusService.save(statusService.mapToStatus(valStatusDTO),request);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody ValStatusDTO valStatusDTO,
                                         HttpServletRequest request){
        return statusService.update(id,statusService.mapToStatus(valStatusDTO),request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request){
        return statusService.delete(id,request);
    }

    @GetMapping
    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0, OtherConfig.getDefaultPaginationSize(), Sort.by("id"));
        return statusService.findAll(pageable,request);
    }
}
