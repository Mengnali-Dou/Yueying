package com.yueying.backendapi.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 活动管理员
 * @TableName tb_event_admin
 */
@Data
@TableName(value ="tb_event_admin")
public class EventAdmin implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long eventAdminId;

    /**
     * 活动id
     */
    private Long eventId;

    /**
     * 用户id
     */
    private Long userId;

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