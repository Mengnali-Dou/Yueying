package com.yueying.backendapi.constant;

/**
 * 影片管理请求信息
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
public class MovieMessage {

    /**
     * http请求错误消息
     * 影片不存在
     */
    public static final String MOVIE_NONENTITY = "影片不存在";

    /**
     * http请求错误信息
     * 影片场次不存在
     */
    public static final String MOVIE_SESSION_NONENTITY = "影片场次不存在";

    /**
     * http请求错误信息
     * 该场次有票未退
     */
    public static final String HAS_TICKETS_HAVE_NOT_BEEN_REFUNDED = "该场次有票未退";
}
