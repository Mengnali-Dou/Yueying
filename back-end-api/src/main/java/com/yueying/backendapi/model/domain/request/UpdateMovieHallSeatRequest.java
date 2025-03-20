package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 修改影厅座位请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class UpdateMovieHallSeatRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -8832916557325877028L;

    /**
     * 座位id
     */
    private Long movieHallSeatId;

    /**
     * 座位类型
     * 0 - 普通座位
     * 1 - 未安装座位
     * 2 - 故障
     */
    private Integer seatType;
}
