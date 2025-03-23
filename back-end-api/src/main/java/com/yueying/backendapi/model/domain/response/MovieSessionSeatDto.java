package com.yueying.backendapi.model.domain.response;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 影片座位信息DTO
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class MovieSessionSeatDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 7925862862406102246L;

    /**
     * id
     */
    private Long movieSessionSeatId;

    /**
     * 场次id
     */
    private Long sessionId;

    /**
     * 排
     */
    private Integer rowNumbers;

    /**
     * 列
     */
    private Integer colNumbers;

    /**
     * 是否已售 0 - 否 1 - 是
     */
    private Integer sold;
}
