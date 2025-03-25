package com.yueying.backendapi.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 活动
 * @TableName tb_event
 */
@Data
@TableName(value ="tb_event")
public class Event implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
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
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date finishTime;

    /**
     * 是否结束 0 - 未结束 1 - 已结束
     */
    private Integer finished;

    /**
     * 活动简介
     */
    private String eventProfile;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除 0 - 未删除 1 - 已删除
     */
    @TableLogic
    private Integer deleted;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}