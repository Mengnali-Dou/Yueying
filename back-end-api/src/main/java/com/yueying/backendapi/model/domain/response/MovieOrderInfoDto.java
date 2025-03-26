package com.yueying.backendapi.model.domain.response;

import lombok.Data;

/**
 * 影片订单信息dto
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class MovieOrderInfoDto {

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
     * 场次信息
     */
    private MovieSessionInfoDto movieSessionInfo;

    /**
     * 座位
     */
    private String seat;

    /**
     * 开始时间
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
     * 下单时间
     */
    private String orderTime;
}
