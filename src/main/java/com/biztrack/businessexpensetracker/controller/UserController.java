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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody ValUserDTO valUserDTO,
                                       HttpServletRequest request){
        return userService.save(userService.mapToUser(valUserDTO),request);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @Valid @RequestBody ValUserDTO valUserDTO,
                                         HttpServletRequest request){
        return userService.update(id,userService.mapToUser(valUserDTO),request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id, HttpServletRequest request){
        return userService.delete(id,request);
    }

    @GetMapping
    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0, OtherConfig.getDefaultPaginationSize(), Sort.by("id"));
        return userService.findAll(pageable,request);
    }
}
