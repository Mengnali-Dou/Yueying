package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 活动订单查询请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class SearchEventOrderRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 5013585024087954800L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 活动id
     */
    private Long eventId;

    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 订单状态
     */
    private Integer orderStatus;
}
