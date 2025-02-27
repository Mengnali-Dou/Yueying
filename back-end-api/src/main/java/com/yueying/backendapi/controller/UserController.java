package com.yueying.backendapi.controller;

import com.yueying.backendapi.model.domain.request.*;
import com.yueying.backendapi.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/update")
    public ResponseEntity<Object> userInfoUpdate(@RequestBody UpdateUserInfoRequest updateUserInfoRequest, HttpServletRequest request) {
        return userService.userInfoUpdate(updateUserInfoRequest, request);
    }

    @PutMapping("/password-reset")
    public ResponseEntity<Object> userPasswordReset(@RequestBody PasswordResetRequest passwordResetRequest, HttpServletRequest request) {
        return userService.userPasswordReset(passwordResetRequest, request);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> userSearch(@RequestParam String userAccount, @RequestParam String userName, HttpServletRequest request) {
        UserSearchRequest userSearchRequest = new UserSearchRequest();
        userSearchRequest.setUserAccount(userAccount);
        userSearchRequest.setUserName(userName);
        return userService.userSearch(userSearchRequest, request);
    }
}
