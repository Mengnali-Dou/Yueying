package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 影片场次查询请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class SearchMovieSessionRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 6428270201899462492L;

    /**
     * 场次id
     */
    private Long sessionId;

    /**
     * 影片id
     */
    private Long movieId;

    /**
     * 影院id
     */
    private Long cinemaId;

    /**
     * 放映时间
     */
    private String movieRunDate;
}
