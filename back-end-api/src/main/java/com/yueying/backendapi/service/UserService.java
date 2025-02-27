package com.yueying.backendapi.service;

import com.yueying.backendapi.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yueying.backendapi.model.domain.request.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_user(用户表)】的数据库操作Service
* @createDate 2025-01-31 16:49:10
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userRegisterRequest 用户注册请求体
     * @return 是否注册成功
     */
    ResponseEntity<Object> userRegister(UserRegisterRequest userRegisterRequest);

    /**
     * 用户登录
     * @param userLoginRequest 用户登录请求体
     * @param request http请求信息
     * @return 登录用户信息（脱敏）
     */
    ResponseEntity<Object> userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request);

    /**
     * 退出登录
     * @param userLogoutRequest 退出登录请求体
     * @param request http请求信息
     * @return 是否退出成功
     */
    ResponseEntity<Object> logout(UserLogoutRequest userLogoutRequest, HttpServletRequest request);

    /**
     * 修改用户信息
     * @param updateUserInfoRequest 修改用户信息请求体
     * @param request http请求信息
     * @return 用户修改后信息（脱敏）
     */
    ResponseEntity<Object> userInfoUpdate(UpdateUserInfoRequest updateUserInfoRequest, HttpServletRequest request);

    /**
     * 用户密码修改
     * @param passwordResetRequest 用户密码修改请求体
     * @param request http请求信息
     * @return 是否修改成功
     */
    ResponseEntity<Object> userPasswordReset(PasswordResetRequest passwordResetRequest, HttpServletRequest request);

    /**
     * 搜索用户
     * @param userSearchRequest 搜索用户请求体
     * @param request http请求信息
     * @return 搜索到的用户列表
     */
    ResponseEntity<Object> userSearch(UserSearchRequest userSearchRequest, HttpServletRequest request);
}
