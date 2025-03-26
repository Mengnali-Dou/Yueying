package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 影片下单请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class BookMovieRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -838732145574715857L;

    /**
     * 场次id
     */
    private Long sessionId;

    /**
     * 座位
     */
    private String seat;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 观影人
     */
    private String spectator;
}
