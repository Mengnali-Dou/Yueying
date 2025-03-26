package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 删除活动请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class DeleteEventRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 151371076043931L;

    /**
     * id
     */
    private Long eventId;
}
