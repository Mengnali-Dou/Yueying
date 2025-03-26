package com.yueying.backendapi.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 活动订单
 * @TableName tb_event_order
 */
@Data
@TableName(value ="tb_event_order")
public class EventOrder implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long orderId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 活动ID
     */
    private Long eventId;

    /**
     * 座位
     */
    private String seat;

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 观演人
     */
    private String spectator;

    /**
     * 订单价格
     */
    private Integer orderPrice;

    /**
     * 订单状态 0 - 未支付 1 - 支付成功 2 - 待检票 2 - 检票完成 3 - 活动结束 4 - 订单结束 5 - 退票申请 6 - 退票成功
     */
    private Integer orderStatus;

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