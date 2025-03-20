package com.yueying.backendapi.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 影厅座位（每个座位一条数据）
 * @TableName tb_movie_hall_seat
 */
@Data
@TableName(value ="tb_movie_hall_seat")
public class MovieHallSeat implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long movieSeatId;

    /**
     * movie_hall_id
     */
    private Long movieHallId;

    /**
     * 排
     */
    private Integer rowNumbers;

    /**
     * 列
     */
    private Integer colNumbers;

    /**
     * 座位类型 0 - 普通座位 1 - 未安装座位 2 - 故障
     */
    private Integer seatType;

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