package com.yueying.backendapi.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 订单
 * @TableName tb_movie_order
 */
@Data
@TableName(value ="tb_movie_order")
public class MovieOrder implements Serializable {
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
     * 场次
     */
    private Long sessionId;

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
     * 订单状态 0 - 未支付 1 - 支付成功 2 - 待取票 3 - 取票完成 4 - 待检票 5 - 检票完成 6 - 订单结束 7 - 退票申请 8 - 退票成功
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