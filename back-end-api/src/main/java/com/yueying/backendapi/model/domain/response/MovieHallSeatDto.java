package com.yueying.backendapi.model.domain.response;

import lombok.Data;

/**
 * 影厅座位信息
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class MovieHallSeatDto {

    /**
     * id
     */
    private Long movieSeatId;

    /**
     * 影厅id
     */
    private Long movieHallId;

    /**
     * 排
     */
    private Integer rowNumbers;

    /**
     * 列
     */
    private Integer colNumbers;

    /**
     * 座位类型 0 - 普通座位 1 - 未安装座位 2 - 故障
     */
    private Integer seatType;
}
