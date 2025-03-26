package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 添加活动请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class AddEventRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -3141036651499006935L;

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
    private String beginTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 活动简介
     */
    private String eventProfile;
}
