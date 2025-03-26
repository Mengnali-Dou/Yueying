package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 查询活动票价信息请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class SearchEventPriceRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 4623397157497932722L;

    /**
     * 活动id
     */
    private Long eventId;
}
