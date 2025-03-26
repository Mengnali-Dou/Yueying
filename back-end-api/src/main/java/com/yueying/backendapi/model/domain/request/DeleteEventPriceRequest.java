package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 删除活动票价请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class DeleteEventPriceRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -4377833876385039569L;

    /**
     * id
     */
    private Long eventPriceId;
}
