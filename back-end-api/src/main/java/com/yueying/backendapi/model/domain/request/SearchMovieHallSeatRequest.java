package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 查询影厅座位请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class SearchMovieHallSeatRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 6959634438306802150L;

    /**
     * 影厅id
     */
    private Long movieHallId;
}
