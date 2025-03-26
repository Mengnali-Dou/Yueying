package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 活动退票申请处理请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class EventRefundManageRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1683697369758620386L;

    /**
     * 订单id
     */
    private Long eventOrderId;

    /**
     * 是否同意
     */
    private Boolean agree;
}
