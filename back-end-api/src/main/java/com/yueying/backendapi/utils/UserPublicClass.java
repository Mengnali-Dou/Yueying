package com.yueying.backendapi.utils;

import org.springframework.util.DigestUtils;

import java.util.regex.Pattern;

import static com.yueying.backendapi.constant.UserConstant.*;

/**
 * 用户管理工具类
 * @author liujiaqi
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
}
