package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 活动订单删除请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class DeleteEventOrderRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -6721940313814270466L;

    /**
     * id
     */
    private Long eventOrderId;
}
