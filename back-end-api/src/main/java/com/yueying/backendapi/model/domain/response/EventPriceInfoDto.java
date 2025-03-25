package com.yueying.backendapi.model.domain.response;

import lombok.Data;

/**
 * 活动票价信息
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class EventPriceInfoDto {

    /**
     * 活动票价id
     */
    private Long priceId;

    /**
     * 活动id
     */
    private Long eventId;

    /**
     * 活动名
     */
    private String eventName;

    /**
     * 座位类型
     */
    private String seatType;

    /**
     * 座位行数
     */
    private Integer seatRows;

    /**
     * 座位列数
     */
    private Integer seatCols;

    /**
     * 价格
     */
    private Integer price;

    /**
     * 余票数
     */
    private Long ticketsLeft;

    /**
     * 总票数
     */
    private Long totalNum;
}
