package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 删除影片类型请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class DeleteMovieTypeRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -952406881458471087L;

    /**
     * id
     */
    private Long movieTypeId;
}
