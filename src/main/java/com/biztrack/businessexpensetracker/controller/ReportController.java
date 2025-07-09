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
    @PreAuthorize("hasAuthority('Report')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValReportDTO valReportDTO,
                                       HttpServletRequest request) {
        return reportService.save(reportService.mapToReport(valReportDTO), request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Report')")
    public ResponseEntity<Object> update(
            @PathVariable Long id,
            @Valid @RequestBody ValReportDTO valReportDTO,
            HttpServletRequest request) {
        return reportService.update(id, reportService.mapToReport(valReportDTO), request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Report')")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {
        return reportService.delete(id, request);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('Report')")
    public ResponseEntity<Object> findAll(HttpServletRequest request) {
        Pageable pageable = PageRequest.of(0, OtherConfig.getDefaultPaginationSize(), Sort.by("id"));
        return reportService.findAll(pageable, request);
    }

    @GetMapping("/{sort}/{sort-by}/{page}")
    @PreAuthorize("hasAuthority('Report')")
    public ResponseEntity<Object> findByParam(
            @PathVariable String sort,
            @PathVariable(value = "sort-by") String sortBy,
            @PathVariable Integer page,
            @RequestParam Integer size,
            @RequestParam String column,
            @RequestParam String value,
            HttpServletRequest request){
        Pageable pageable = PageRequest.of(page, size, getSort(sortBy, sort));
        return reportService.findByParam(pageable,column,value,request);
    }

    private Sort getSort(String sortBy, String sort) {
        sortBy = switch (sortBy){
            case "purpose", "description"-> "request." + sortBy;
            case "full-name"-> "request.user.fullName";
            default -> sortBy;
        };

        if(sort.equals("desc")){
            return Sort.by(sortBy).descending();
        }else {
            return Sort.by(sortBy);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Report')")
    public ResponseEntity<Object> findById(
            @PathVariable Long id,
            HttpServletRequest request){
        return reportService.findById(id,request);
    }


    @PutMapping("/cancel/{id}")
    @PreAuthorize("hasAuthority('Report')")
    public ResponseEntity<Object> cancel(@PathVariable Long id, HttpServletRequest request){
        return reportService.cancel(id, request);
    }

    @PostMapping("/reject")
    @PreAuthorize("hasAuthority('Approve Report')")
    public ResponseEntity<Object> reject(
            @Valid @RequestBody ValRejectExpensesDTO valRejectExpensesDTO,
            HttpServletRequest request
    ){
        return reportService.reject(valRejectExpensesDTO.getId(), valRejectExpensesDTO.getComment() ,request);
    }

    @PutMapping("/approve/{id}")
    @PreAuthorize("hasAuthority('Approve Report')")
    public ResponseEntity<Object> approve(@PathVariable Long id, HttpServletRequest request){
        return reportService.approve(id, request);
    }
}
