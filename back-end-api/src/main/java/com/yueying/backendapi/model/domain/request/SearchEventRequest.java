package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 活动查询请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class SearchEventRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 7438295437127474897L;

    /**
     * 活动名
     */
    private String eventName;

    /**
     * 活动场地id
     */
    private Long eventPlaceId;

    /**
     * 活动类型
     */
    private String eventType;
}
