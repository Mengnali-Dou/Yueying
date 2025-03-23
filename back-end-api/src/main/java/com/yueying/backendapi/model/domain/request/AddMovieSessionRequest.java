package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 添加影片场次请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class AddMovieSessionRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -2825219901666034133L;

    /**
     * 影片ID
     */
    private Long movieId;

    /**
     * 影厅ID
     */
    private Long hallId;

    /**
     * 放映时间
     */
    private String movieRuntime;

    /**
     * 票价
     */
    private Integer price;
}
