package com.yueying.backendapi.model.domain.response;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 活动信息dto
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class EventInfoDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 940780066738656118L;

    /**
     * 活动ID
     */
    private Long eventId;

    /**
     * 活动名
     */
    private String eventName;

    /**
     * 活动场地Id
     */
    private Long eventPlaceId;

    /**
     * 活动场地名
     */
    private String eventPlaceName;

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
    private String beginDate;

    /**
     * 结束时间
     */
    private String endDate;

    /**
     * 是否结束
     */
    private boolean finished;

    /**
     * 活动简介
     */
    private String eventProfile;
}
