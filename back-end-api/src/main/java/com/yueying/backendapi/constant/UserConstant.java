package com.yueying.backendapi.constant;

/**
 * 用户常量
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
public class UserConstant {

    /**
     * 密码强度验证正则
     * 包含数字、字母、特殊字符
     * 长度8～20位
     */
    public static final String PASSWORD_REGX = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[^\\da-zA-Z\\s]).{8,20}$";

    /**
     * http请求错误信息
     * 密码强度不足
     */
    public static final String LOW_PASSWORD_STRENGTH = "密码强度不足";

    /**
     * 盐
     */
    public static final String SALT = "password-salt";

    /**
     * http请求成功信息
     * 插入数据成功
     */
    public static final String REGISTER_SUCCESSFULLY = "注册成功";

    /**
     * http请求错误信息
     * 用户不存在
     */
    public static final String USER_DOES_NOT_EXISTS = "用户不存在";

    /**
     * http请求错误信息
     * 密码错误
     */
    public static final String PASSWORD_ERROR = "密码错误";

    /**
     * 用户登陆态键
     */
    public static final String USER_LOGIN_STATE = "userLoginState";

    /**
     * http请求成功信息
     * 登陆成功
     */
    public static final String LOGIN_SUCCESSFULLY = "登陆成功";
}
