package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 添加活动票价请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class AddEventPriceRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 7317040846664616482L;

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
