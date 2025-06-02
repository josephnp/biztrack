package com.biztrack.businessexpensetracker.controller;

import com.biztrack.businessexpensetracker.config.OtherConfig;
import com.biztrack.businessexpensetracker.dto.validation.ValRejectExpensesDTO;
import com.biztrack.businessexpensetracker.dto.validation.ValReportDTO;
import com.biztrack.businessexpensetracker.service.ReportService;
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
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @PostMapping
    @PreAuthorize("hasAuthority('Staff')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValReportDTO valReportDTO,
                                       HttpServletRequest request) {
        return reportService.save(reportService.mapToReport(valReportDTO), request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Staff')")
    public ResponseEntity<Object> update(
            @PathVariable Long id,
            @Valid @RequestBody ValReportDTO valReportDTO,
            HttpServletRequest request) {
        return reportService.update(id, reportService.mapToReport(valReportDTO), request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Staff')")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {
        return reportService.delete(id, request);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('Staff')")
    public ResponseEntity<Object> findAll(HttpServletRequest request) {
        Pageable pageable = PageRequest.of(0, OtherConfig.getDefaultPaginationSize(), Sort.by("id"));
        return reportService.findAll(pageable, request);
    }

    @PutMapping("/cancel/{id}")
    @PreAuthorize("hasAuthority('Staff')")
    public ResponseEntity<Object> cancel(@PathVariable Long id, HttpServletRequest request){
        return reportService.cancel(id, request);
    }

    @PostMapping("/reject")
    @PreAuthorize("hasAuthority('Finance')")
    public ResponseEntity<Object> reject(
            @Valid @RequestBody ValRejectExpensesDTO valRejectExpensesDTO,
            HttpServletRequest request
    ){
        return reportService.reject(valRejectExpensesDTO.getId(), valRejectExpensesDTO.getComment() ,request);
    }

    @PutMapping("/approve/{id}")
    @PreAuthorize("hasAuthority('Finance')")
    public ResponseEntity<Object> approve
            (@PathVariable Long id, HttpServletRequest request){
        return reportService.approve(id, request);
    }
}
