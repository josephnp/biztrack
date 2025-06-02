package com.biztrack.businessexpensetracker.service;

import com.biztrack.businessexpensetracker.config.JwtConfig;
import com.biztrack.businessexpensetracker.dto.validation.LoginDTO;
import com.biztrack.businessexpensetracker.handler.ResponseHandler;
import com.biztrack.businessexpensetracker.model.User;
import com.biztrack.businessexpensetracker.repo.UserRepo;
import com.biztrack.businessexpensetracker.security.BcryptCustom;
import com.biztrack.businessexpensetracker.security.Crypto;
import com.biztrack.businessexpensetracker.security.JwtUtility;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class
AuthService
implements UserDetailsService
 {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BcryptCustom bcrypt;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtUtility jwtUtility;

    // Login service

    public ResponseEntity<Object> login(User user, HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>();
        User userNext;
        try {
            String userEmail = user.getEmail();
            Optional<User> opUser = userRepo.findByEmail(userEmail);

            if (!opUser.isPresent()) {
                return new ResponseHandler().handleResponse("User Tidak Ditemukan", HttpStatus.BAD_REQUEST, null, "AUT011", request);
            }

            userNext = opUser.get();

            if (!bcrypt.verifyHash(user.getPassword() + user.getEmail(), userNext.getPassword())) {
                return new ResponseHandler().handleResponse("Username atau Password Salah !!", HttpStatus.BAD_REQUEST, null, "AUT012", request);
            }
        } catch (Exception e) {
            return new ResponseHandler().handleResponse("Terjadi Kesalahan Pada Server", HttpStatus.INTERNAL_SERVER_ERROR, null,
                    "AUT013", request);
        }

        // Ini perlu buat menu dan token JWT

        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("email", userNext.getEmail());
        tokenData.put("id", userNext.getId());
        tokenData.put("employeeNumber", userNext.getEmployeeNumber());
        tokenData.put("fullName", userNext.getFullName());
        String token = jwtUtility.generateToken(tokenData, user.getEmail());

        if (JwtConfig.getTokenEncryptEnable().equals("y")) {
            token = Crypto.performEncrypt(token);
        }

        data.put("token", token);
        return new ResponseHandler().handleResponse("Login Berhasil !!", HttpStatus.OK, data, null, request);
    }


    public User mapToUser(LoginDTO loginDTO) {
        return modelMapper.map(loginDTO, User.class);
    }

@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opUser = userRepo.findByEmail(username);
        if (!opUser.isPresent()) {
            throw new UsernameNotFoundException("Username atau Password Salah !!!");
        }
        User user = opUser.get();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

}
