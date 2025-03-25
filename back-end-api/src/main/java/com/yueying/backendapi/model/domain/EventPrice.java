package com.yueying.backendapi.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 活动票价
 * @TableName tb_event_price
 */
@Data
@TableName(value ="tb_event_price")
public class EventPrice implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long priceId;

    /**
     * 活动ID
     */
    private Long eventId;

    /**
     * 座位类型
     */
    private String seatType;

    /**
     * 排数
     */
    private Integer seatRows;

    /**
     * 列数
     */
    private Integer seatCols;

    /**
     * 票价
     */
    private Integer price;

    /**
     * 余票
     */
    private Long ticketsLeft;

    /**
     * 总票数
     */
    private Long totalNum;

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