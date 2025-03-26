package com.yueying.backendapi.constant;

/**
 * 订单管理
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
public class OrderConstant {

    /**
     * http请求失败信息
     * 余票不足
     */
    public static final String INSUFFICIENT_BALANCE = "余票不足";

    /**
     * http请求错误信息
     * 座位不存在
     */
    public static final String SEAT_NOT_FOUND = "座位不存在";

    /**
     * http请求错误信息
     * 座位已售出
     */
    public static final String SEAT_HAS_SOLD = "座位已售出";

    /**
     * http请求错误信息
     * 下单失败
     */
    public static final String BOOK_FAILED = "下单失败";

    /**
     * http请求成功信息
     * 下单成功
     */
    public static final String BOOK_SUCCESSFULLY = "下单成功";

    /**
     * http请求失败信息
     * 订单不存在
     */
    public static final String ORDER_NOT_FOUND = "订单不存在";

    /**
     * http请求失败信息
     * 退票失败
     */
    public static final String REFUND_FAILED = "退票申请失败";

    /**
     * http请求失败信息
     * 退票申请成功
     */
    public static final String REFUND_SUCCESSFULLY = "退票申请成功";

    /**
     * http请求失败信息
     * 处理失败
     */
    public static final String MANAGE_FAILED = "处理失败";

    /**
     * http请求成功信息
     * 处理成功
     */
    public static final String MANAGE_SUCCESSFULLY = "处理成功";

    /**
     * 订单状态--退票申请
     */
    public static final Integer ORDER_STATUS_REFUND_REQUEST = 7;

    /**
     * 订单状态--退票成功
     */
    public static final Integer ORDER_STATUS_REFUND_SUCCESSFUL = 8;

    /**
     * 订单状态--退票申请失败
     */
    public static final Integer ORDER_STATUS_REFUND_REQUEST_FAILED = 9;
}
