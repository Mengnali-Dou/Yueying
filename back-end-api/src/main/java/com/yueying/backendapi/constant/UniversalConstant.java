package com.yueying.backendapi.constant;

/**
 * 请求消息
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
public class UniversalConstant {

    /**
     * http请求错误信息
     * 必要的参数不能为空
     */
    public static final String PARAMETER_CANNOT_BE_NULL = "必要的参数不能为空";

    /**
     * http请求错误信息
     * 账号长度不足
     */
    public static final String LOW_ACCOUNT_LENGTH = "账号长度不足";

    /**
     * http请求错误信息
     * 账号不能包含特殊字符
     */
    public static final String ACCOUNT_CANNOT_CONTAIN_SPECIAL_CHARACTERS = "账号不能包含特殊字符";

    /**
     * http请求错误信息
     * 账号已注册
     */
    public static final String ACCOUNT_HAS_BEEN_REGISTERED = "账号已注册";

    /**
     * http请求错误信息
     * 插入数据失败
     */
    public static final String FAILED_TO_INSERT = "The server failed to insert data.";

    /**
     * http请求错误信息
     * 权限不足
     */
    public static final String INSUFFICIENT_AUTHORITY = "权限不足";

    /**
     * http请求成功信息
     * 修改成功
     */
    public static final String UPDATE_SUCCESSFULLY = "修改成功";
}
