package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 删除影片订单
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class DeleteMovieOrderRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 4423982268179839117L;

    /**
     * id
     */
    private Long movieOrderId;
}
