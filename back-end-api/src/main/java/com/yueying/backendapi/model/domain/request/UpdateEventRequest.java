package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 修改活动信息请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class UpdateEventRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -4827340368340284286L;

    /**
     * 活动ID
     */
    private Long eventId;

    /**
     * 活动名
     */
    private String eventName;

    /**
     * 活动场地ID
     */
    private Long eventPlaceId;

    /**
     * 活动类型
     */
    private String eventType;

    /**
     * 主要演员
     */
    private String mainActor;

    /**
     * 活动封面（小）
     */
    private String eventCoverSmall;

    /**
     * 活动封面（大）
     */
    private String eventCoverLarge;

    /**
     * 开始时间
     */
    private String beginDateTime;

    /**
     * 结束时间
     */
    private String endDateTime;

    /**
     * 活动简介
     */
    private String eventProfile;
}
