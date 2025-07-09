package com.biztrack.businessexpensetracker.controller;

import com.biztrack.businessexpensetracker.config.OtherConfig;
import com.biztrack.businessexpensetracker.dto.validation.ValUserDTO;
import com.biztrack.businessexpensetracker.service.UserService;
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
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValUserDTO valUserDTO,
                                       HttpServletRequest request){
        return userService.save(userService.mapToUser(valUserDTO),request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody ValUserDTO valUserDTO,
                                         HttpServletRequest request){
        return userService.update(id,userService.mapToUser(valUserDTO),request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request){
        return userService.delete(id,request);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0, OtherConfig.getDefaultPaginationSize(), Sort.by("id"));
        return userService.findAll(pageable,request);
    }

    @GetMapping("/{sort}/{sort-by}/{page}")
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<Object> findByParam(
            @PathVariable String sort,
            @PathVariable(value = "sort-by") String sortBy,
            @PathVariable Integer page,
            @RequestParam Integer size,
            @RequestParam String column,
            @RequestParam String value,
            HttpServletRequest request){
        Pageable pageable = PageRequest.of(page, size, getSort(sortBy, sort));
        return userService.findByParam(pageable,column,value,request);
    }

    private Sort getSort(String sortBy, String sort) {
        sortBy = switch (sortBy){
            case "role"-> "role.name";
            case "full-name"-> "fullName";
            default -> sortBy;
        };

        if(sort.equals("desc")){
            return Sort.by(sortBy).descending();
        }else {
            return Sort.by(sortBy);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<Object> findById(
            @PathVariable Long id,
            HttpServletRequest request){
        return userService.findById(id,request);
    }
}
