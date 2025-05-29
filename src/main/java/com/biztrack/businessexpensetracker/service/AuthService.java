package com.biztrack.businessexpensetracker.service;

import com.biztrack.businessexpensetracker.dto.validation.LoginDTO;
import com.biztrack.businessexpensetracker.dto.validation.ValAddUserDTO;
import com.biztrack.businessexpensetracker.handler.ResponseHandler;
import com.biztrack.businessexpensetracker.model.User;
import com.biztrack.businessexpensetracker.repo.UserRepo;
import com.biztrack.businessexpensetracker.security.BcryptCustom;
import com.biztrack.businessexpensetracker.security.Crypto;
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

    private Crypto crypto = new Crypto();

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
            // enkripsi password doang, kalau mau aman bisa kita enkripsi + email juga
            user.setPassword(bcrypt.hash(user.getPassword()));
            System.out.println(user.getPassword());
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

            String pwdDB = crypto.performDecrypt(userNext.getPassword());
            if (!user.getPassword().equals(pwdDB)) {
                return new ResponseHandler().handleResponse("Username atau Password Salah !!", HttpStatus.BAD_REQUEST, null, "AUT012", request);
            }
        } catch (Exception e) {
            return new ResponseHandler().handleResponse("Terjadi Kesalahan Pada Server", HttpStatus.INTERNAL_SERVER_ERROR, null,
                    "AUT013", request);
        }

        /* Ini perlu buat menu dan token JWT */

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
