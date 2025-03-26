package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 影片退票请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class MovieRefundRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -3982492195877702682L;

    /**
     * 影片订单id
     */
    private Long movieOrderId;
}
