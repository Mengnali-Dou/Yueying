package com.yueying.backendapi.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 活动场地
 * @TableName tb_event_place
 */
@Data
@TableName(value ="tb_event_place")
public class EventPlace implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long placeId;

    /**
     * 场地名
     */
    private String placeName;

    /**
     * 场地类型
     */
    private String placeType;

    /**
     * 场地地址
     */
    private String placeAddress;

    /**
     * 最大容纳人数
     */
    private Long maxSeats;

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