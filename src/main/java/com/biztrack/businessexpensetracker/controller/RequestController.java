package com.biztrack.businessexpensetracker.controller;

import com.biztrack.businessexpensetracker.config.OtherConfig;
import com.biztrack.businessexpensetracker.dto.validation.ValRejectExpensesDTO;
import com.biztrack.businessexpensetracker.dto.validation.ValRequestDTO;
import com.biztrack.businessexpensetracker.service.RequestService;
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
@RequestMapping("/request")
public class RequestController {
    @Autowired
    private RequestService requestService;

    @PostMapping
    @PreAuthorize("hasAuthority('Request')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValRequestDTO valRequestDTO,
                                       HttpServletRequest request) {
        return requestService.save(requestService.mapToRequest(valRequestDTO), request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Request')")
    public ResponseEntity<Object> update(
            @PathVariable Long id,
            @Valid @RequestBody ValRequestDTO valRequestDTO,
            HttpServletRequest request) {
        return requestService.update(id, requestService.mapToRequest(valRequestDTO), request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Request')")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {
        return requestService.delete(id, request);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('Request')")
    public ResponseEntity<Object> findAll(HttpServletRequest request) {
        Pageable pageable = PageRequest.of(0, OtherConfig.getDefaultPaginationSize(), Sort.by("id"));
        return requestService.findAll(pageable, request);
    }

    @GetMapping("/{sort}/{sort-by}/{page}")
    @PreAuthorize("hasAuthority('Request')")
    public ResponseEntity<Object> findByParam(
            @PathVariable String sort,
            @PathVariable(value = "sort-by") String sortBy,
            @PathVariable Integer page,
            @RequestParam Integer size,
            @RequestParam String column,
            @RequestParam String value,
            HttpServletRequest request){
        Pageable pageable = PageRequest.of(page, size, getSort(sortBy, sort).descending());
        return requestService.findByParam(pageable,column,value,request);
    }

    private Sort getSort(String sortBy, String sort) {
        sortBy = switch (sortBy){
            case "full-name"-> "user.fullName";
            default -> sortBy;
        };

        if(sort.equals("desc")){
            return Sort.by(sortBy).descending();
        }else {
            return Sort.by(sortBy);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Request')")
    public ResponseEntity<Object> findById(
            @PathVariable Long id,
            HttpServletRequest request){
        return requestService.findById(id,request);
    }

    @PutMapping("/cancel/{id}")
    @PreAuthorize("hasAuthority('Request')")
    public ResponseEntity<Object> cancel(@PathVariable Long id, HttpServletRequest request){
        return requestService.cancel(id, request);
    }

    @PostMapping("/reject")
    @PreAuthorize("hasAuthority('Approve Request')")
    public ResponseEntity<Object> reject(
            @Valid @RequestBody ValRejectExpensesDTO valRejectExpensesDTO,
            HttpServletRequest request
    ){
        return requestService.reject(valRejectExpensesDTO.getId(), valRejectExpensesDTO.getComment() ,request);
    }

    @PutMapping("/approve-finance/{id}")
    @PreAuthorize("hasAuthority('Approve Request')")
    public ResponseEntity<Object> approveFinance(@PathVariable Long id, HttpServletRequest request){
        return requestService.approve(id, request);
    }

    @PutMapping("/approve-manager/{id}")
    @PreAuthorize("hasAuthority('Approve Request')")
    public ResponseEntity<Object> approveManager(@PathVariable Long id, HttpServletRequest request){
        return requestService.approveManager(id, request);
    }
}
