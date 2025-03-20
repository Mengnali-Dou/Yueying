package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 添加影厅座位请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class AddMovieHallSeatRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -8832916557325877028L;

    /**
     * 影厅id
     */
    private Long movieHallId;

    /**
     * 座位--行
     */
    private Integer rowNumbers;

    /**
     * 座位--列
     */
    private Integer colNumbers;

    /**
     * 座位类型
     * 0 - 普通座位
     * 1 - 未安装座位
     * 2 - 故障
     */
    private Integer seatType;
}
