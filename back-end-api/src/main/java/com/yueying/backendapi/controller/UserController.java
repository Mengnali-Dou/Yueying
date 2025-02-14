package com.yueying.backendapi.controller;

import com.yueying.backendapi.model.domain.request.UserLoginRequest;
import com.yueying.backendapi.model.domain.request.UserLogoutRequest;
import com.yueying.backendapi.model.domain.request.UserRegisterRequest;
import com.yueying.backendapi.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        return userService.userRegister(userRegisterRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest httpServletRequest) {
        return userService.userLogin(userLoginRequest, httpServletRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@RequestBody UserLogoutRequest userLogoutRequest, HttpServletRequest request) {
        return userService.logout(userLogoutRequest, request);
    }
}
