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
     * 用户登录态键
     */
    public static final String USER_LOGIN_STATE = "userLoginState";

    /**
     * http请求成功信息
     * 登录成功
     */
    public static final String LOGIN_SUCCESSFULLY = "登录成功";

    /**
     * http请求错误信息
     * 用户ID不能为空
     */
    public static final String USER_ID_CANNOT_BE_EMPTY = "用户ID不能为空";

    /**
     * http请求错误信息
     * 用户未登录
     */
    public static final String USER_NOT_LOGGED_IN = "用户未登录";

    /**
     * http请求成功信息
     * 退出成功
     */
    public static final String LOGOUT_SUCCESSFULLY = "退出成功";

    /**
     * http请求错误信息
     * 发送请求用户和退出登录用户不匹配
     */
    public static final String LOGOUT_USER_ID_MISMATCH = "请求的用户ID与要退出登录的用户ID不匹配。请确保使用正确的用户ID进行退出登录操作。";
}
