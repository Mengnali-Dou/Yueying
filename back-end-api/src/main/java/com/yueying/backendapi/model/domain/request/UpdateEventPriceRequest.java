package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 修改活动票价信息请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class UpdateEventPriceRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -408400962328495519L;

    /**
     * 票价id
     */
    private Long priceId;

    /**
     * 活动id
     */
    private Long eventId;

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
}
