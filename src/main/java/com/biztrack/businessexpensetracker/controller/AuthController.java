package com.biztrack.businessexpensetracker.controller;

import com.biztrack.businessexpensetracker.config.JwtConfig;
import com.biztrack.businessexpensetracker.dto.validation.LoginDTO;
import com.biztrack.businessexpensetracker.dto.validation.ValAddUserDTO;
import com.biztrack.businessexpensetracker.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;


    @PostMapping("/add-user")
    public ResponseEntity<Object> addNewUser(@Valid @RequestBody ValAddUserDTO valAddUserDTO, HttpServletRequest request) {
        return authService.addUser(authService.mapToUser(valAddUserDTO), request);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        return authService.login(authService.mapToUser(loginDTO), request);
    }
    
}
