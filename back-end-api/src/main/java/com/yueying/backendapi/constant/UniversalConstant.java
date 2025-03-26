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

    /**
     * http请求错误信息
     * 修改数据失败
     */
    public static final String FAILED_TO_UPDATE = "修改失败";

    /**
     * 用户角色--管理员
     */
    public static final int ROLE_ADMIN = 1;

    /**
     * 用户角色--影院管理员
     */
    public static final int ROLE_CINEMA_ADMIN = 2;

    /**
     * 用户角色--活动管理员
     */
    public static final int ROLE_EVENT_ADMIN = 3;

    /**
     * http请求成功信息
     * 查询成功
     */
    public static final String SEARCH_SUCCESSFULLY = "查询成功";

    /**
     * http请求失败信息
     * 删除失败
     */
    public static final String DELETE_FAILED = "删除失败";

    /**
     * http请求成功信息
     * 删除成功
     */
    public static final String DELETE_SUCCESSFULLY = "删除成功";

    /**
     * http请求成功信息
     * 添加成功
     */
    public static final String INSERT_SUCCESSFULLY = "添加成功";
}
