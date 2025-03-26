package com.yueying.backendapi.model.domain.request;

import lombok.Data;

/**
 * 活动下单请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class BookEventRequest {

    /**
     * 活动id
     */
    private Long eventId;

    /**
     * 座位
     */
    private String seat;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 观演人
     */
    private String spectator;
}
