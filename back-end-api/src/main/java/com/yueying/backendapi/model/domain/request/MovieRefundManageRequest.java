package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 影片退票处理请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class MovieRefundManageRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 4450166980794089955L;

    /**
     * 订单id
     */
    private Long movieOrderId;

    /**
     * 是否同意
     */
    private Boolean agree;
}
