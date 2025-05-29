package com.biztrack.businessexpensetracker.service;

import com.biztrack.businessexpensetracker.config.JwtConfig;
import com.biztrack.businessexpensetracker.dto.validation.LoginDTO;
import com.biztrack.businessexpensetracker.dto.validation.ValAddUserDTO;
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
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    UserRepo repo;

    BcryptCustom bcrypt = new BcryptCustom(12);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtUtility jwtUtility;


    /* Regis service */
    public ResponseEntity<Object> addUser(User user, HttpServletRequest request) {
        if (user == null) {
            return new ResponseHandler().handleResponse("Email Tidak Ditemukan !!", HttpStatus.BAD_REQUEST, null, "AUT001", request);
        }

        if (user.getEmail() == null) {
            return new ResponseHandler().handleResponse("Email Tidak Ditemukan !!", HttpStatus.BAD_REQUEST, null, "AUT002", request);
        }

        Map<String, Object> m = new HashMap<>();

        try {
            user.setPassword(bcrypt.hash(user.getPassword() + user.getEmail()));
            repo.save(user);

        } catch (Exception e) {
            return new ResponseHandler().handleResponse("Server Tidak Dapat Memproses !!", HttpStatus.INTERNAL_SERVER_ERROR, null, "AUT00FE001", request);
        }
        return new ResponseHandler().handleResponse("Registrasi berhasil", HttpStatus.OK, m, null, request);
    }


    /* Login service */
    public ResponseEntity<Object> login(User user, HttpServletRequest request) {
        Map<String, Object> m = new HashMap<>();
        User userNext = null;
        try {
            String userEmail = user.getEmail();
            Optional<User> opUser = repo.findByEmail(userEmail);

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

        /* Ini perlu buat menu dan token JWT */
        Map<String, Object> mapData = new HashMap<>();
        mapData.put("em", user.getEmail());
//        mapData.put("id", user.getId());
//        mapData.put("emp", user.getEmployeeNumber());
//        mapData.put("dep", user.getDepartment());
//        mapData.put("rol", user.getRole());
//        mapData.put("fn", user.getFullName());
        String token = jwtUtility.generateToken(mapData, user.getEmail());

        if (JwtConfig.getTokenEncryptEnable().equals("y")) {
            token = Crypto.performEncrypt(token);
        }

        m.put("token", token);

        return new ResponseHandler().handleResponse("Login Berhasil !!", HttpStatus.OK, m, null, request);
    }


    /* Model Mapper */
    public User mapToUser(ValAddUserDTO valAddUserDTO) {
        return modelMapper.map(valAddUserDTO, User.class);
    }

    public User mapToUser(LoginDTO loginDTO) {
        return modelMapper.map(loginDTO, User.class);
    }
}
