package com.yueying.backendapi.constant;

public class EventConstant {

    /**
     * 活动状态--未结束
     */
    public static final Integer EVENT_STATUS_UNFINISHED = 0;

    /**
     * http请求错误信息
     * 活动不存在
     */
    public static final String EVENT_NONENTITY = "活动不存在";

    /**
     * http请求错误消息
     * 活动票价已存在
     */
    public static final String EVENT_PRICE_HAS_ADDED = "活动票价已存在";

    /**
     * http请求错误消息
     * 活动票价不存在
     */
    public static final String EVENT_PRICE_NONENTITY = "活动票价不存在";

    /**
     * http请求错误信息
     * 有票未退
     */
    public static final String HAS_TICKETS_HAVE_NOT_BEEN_REFUNDED = "有票未退";
}
