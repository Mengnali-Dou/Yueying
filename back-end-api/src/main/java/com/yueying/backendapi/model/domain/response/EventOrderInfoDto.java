package com.yueying.backendapi.model.domain.response;

import lombok.Data;

/**
 * 活动订单信息dto
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class EventOrderInfoDto {

    /**
     * id
     */
    private Long orderId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 活动ID
     */
    private Long eventId;

    /**
     * 活动名
     */
    private String eventName;

    /**
     * 座位
     */
    private String seat;

    /**
     * 开始时间 yyyy-mm-dd hh:mm:ss
     */
    private String beginTime;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 观演人
     */
    private String spectator;

    /**
     * 订单价格
     */
    private Integer orderPrice;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 下单时间 yyyy-mm-dd hh:mm:ss
     */
    private String createTime;
}
