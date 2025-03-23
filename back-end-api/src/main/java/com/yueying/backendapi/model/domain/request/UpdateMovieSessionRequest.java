package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 修改影片场次信息请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class UpdateMovieSessionRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -3523081222645899261L;

    /**
     * 场次id
     */
    private Long sessionId;

    /**
     * 开始时间
     */
    private String movieRuntime;

    /**
     * 价格
     */
    private Integer price;
}
