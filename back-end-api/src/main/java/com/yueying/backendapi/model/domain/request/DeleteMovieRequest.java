package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 删除影片请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class DeleteMovieRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 3118553840139522901L;

    /**
     * 影片id
     */
    private Long movieId;
}
