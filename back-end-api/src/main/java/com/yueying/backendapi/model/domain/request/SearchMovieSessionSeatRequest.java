package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 搜索影片场次座位请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class SearchMovieSessionSeatRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 4730713557422860089L;

    /**
     * 影片场次id
     */
    private Long movieSessionId;
}
