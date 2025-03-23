package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 删除影片场次请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class DeleteMovieSessionRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -9114581302892043068L;

    /**
     * id
     */
    private Long movieSessionId;
}
