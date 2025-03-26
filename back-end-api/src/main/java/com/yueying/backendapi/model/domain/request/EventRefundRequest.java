package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 活动退票申请请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class EventRefundRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 5685706714155226682L;

    /**
     * 活动订单id
     */
    private Long eventOrderId;
}
