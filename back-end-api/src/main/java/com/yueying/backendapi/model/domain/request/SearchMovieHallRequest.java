package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 查询影厅请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class SearchMovieHallRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -1379269477615062180L;

    /**
     * 影院id
     */
    private Long cinemaId;
}
