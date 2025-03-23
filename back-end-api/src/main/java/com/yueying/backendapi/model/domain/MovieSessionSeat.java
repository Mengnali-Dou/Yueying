package com.yueying.backendapi.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 电影场次座位
 * @TableName tb_movie_session_seat
 */
@Data
@TableName(value ="tb_movie_session_seat")
public class MovieSessionSeat implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long movieSessionSeatId;

    /**
     * 场次id
     */
    private Long sessionId;

    /**
     * 排
     */
    private Integer rowNumbers;

    /**
     * 列
     */
    private Integer colNumbers;

    /**
     * 是否已售 0 - 否 1 - 是
     */
    private Integer sold;

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