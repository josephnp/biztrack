package com.biztrack.businessexpensetracker.service;

import com.biztrack.businessexpensetracker.handler.ResponseHandler;
import com.biztrack.businessexpensetracker.model.User;
import com.biztrack.businessexpensetracker.repo.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
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

    /* Login service */
    public ResponseEntity<Object> login(User user, HttpServletRequest request) {
        Map<String, Object> m = new HashMap<>();
        User userNext = null;
        try {
            String userEmail = user.getEmail();
            Optional<User> opUser = repo.findByEmail(userEmail);

            if (!opUser.isPresent()) {
                return new ResponseHandler().handleResponse("User Tidak Ditemukan", HttpStatus.BAD_REQUEST, null, "AUT001", request);
            }

            userNext = opUser.get();

            String pwdDB = userNext.getPassword();
            if (!userNext.getPassword().equals(pwdDB)) {
                return new ResponseHandler().handleResponse("Username atau Password Salah !!", HttpStatus.BAD_REQUEST, null, "AUT002", request);
            }
        } catch (Exception e) {
            return new ResponseHandler().handleResponse("Terjadi Kesalahan Pada Server", HttpStatus.INTERNAL_SERVER_ERROR, null,
                    "AUT00FE021", request);
        }

        /* Ini perlu buat menu dan token JWT */

        return new ResponseHandler().handleResponse("Login Berhasil !!", HttpStatus.OK, m, null, request);
    }
}
