package com.yueying.backendapi.constant;

/**
 * Http请求状态码
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
public class ResponseStatus {
    /**
     * 请求成功
     */
    public static final int OK = 200;

    /**
     * 请求错误
     */
    public static final int BAD_REQUEST = 400;

    /**
     * 要求身份认证
     */
    public static final int UNAUTHORIZED = 401;

    /**
     * 拒绝请求
     */
    public static final int FORBIDDEN = 403;

    /**
     * 没有找到
     */
    public static final int NOT_FOUND = 404;

    /**
     * 请求超时
     */
    public static final int REQUEST_TIME_OUT = 408;

    /**
     * 发生冲突
     */
    public static final int CONFLICT = 409;

    /**
     * 请求资源不存在
     */
    public static final int GONE = 410;

    /**
     * 服务器错误
     */
    public static final int INTERNAL_SERVER_ERROR = 500;
}
