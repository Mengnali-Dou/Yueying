package com.yueying.backendapi.controller;

import com.yueying.backendapi.model.domain.request.UserRegisterRequest;
import com.yueying.backendapi.service.UserService;
import jakarta.annotation.Resource;
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
}
