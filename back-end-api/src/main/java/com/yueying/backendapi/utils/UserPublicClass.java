package com.yueying.backendapi.utils;

import com.yueying.backendapi.model.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.DigestUtils;

import java.util.regex.Pattern;

import static com.yueying.backendapi.constant.UniversalConstant.*;
import static com.yueying.backendapi.constant.UserConstant.*;

/**
 * 用户管理工具类
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
public class UserPublicClass {

    /**
     * 用户账号不包含特殊字符
     * @param account 账号
     * @return 是否包含特殊字符
     */
    public static boolean accountRegx(String account) {
        String regx = "^[a-zA-Z0-9_-]+$";
        return Pattern.compile(regx).matcher(account).find();
    }

    /**
     * 密码强度校验
     * @param password 密码
     * @return 是否符合强度
     */
    public static boolean cryptographicStrengthCheck(String password) {
        return !Pattern.compile(PASSWORD_REGX).matcher(password).find();
    }

    /**
     * 密码加密--md5
     * @param password 密码
     * @param account 账号
     * @return 加密后的密码
     */
    public static String digestPassword(String password, String account) {
        return DigestUtils.md5DigestAsHex((SALT + password + account).getBytes());
    }

    /**
     * 获取登录用户id
     * @param request http请求信息
     * @return 登录用户id
     */
    public static Long getUserId(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user.getUserId();
    }

    /**
     * 判断用户是否为管理员
     * @param request http请求信息
     * @return 是否为管理员
     */
    public static boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user == null || user.getUserRole() != ROLE_ADMIN;
    }

    /**
     * 判断用户是否为影院管理员
     * @param request http请求信息
     * @return 是否为影院管理员
     */
    public static boolean isCinemaAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user == null || user.getUserRole() != ROLE_CINEMA_ADMIN;
    }

    /**
     * 判断用户是否为活动管理员
     * @param request http请求信息
     * @return 是否为活动管理员
     */
    public static boolean isEventAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user == null || user.getUserRole() != ROLE_EVENT_ADMIN;
    }

    /**
     * 判断是否为当前登陆用户
     * @param userId 用户ID
     * @param request http请求信息
     * @return 是否为当前登陆用户
     */
    public static boolean isCurrentUser(long userId, HttpServletRequest request) {
        Object object = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) object;
        return user.getUserId() != userId;
    }
}
