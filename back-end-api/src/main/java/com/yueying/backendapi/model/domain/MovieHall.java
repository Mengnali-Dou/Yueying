package com.yueying.backendapi.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 影厅
 * @TableName tb_movie_hall
 */
@Data
@TableName(value ="tb_movie_hall")
public class MovieHall implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long movieHallId;

    /**
     * 影院ID
     */
    private Long cinemaId;

    /**
     * 影厅名
     */
    private String movieHallName;

    /**
     * 影厅类型ID
     */
    private Long movieHallTypeId;

    /**
     * 影厅照片
     */
    private String movieHallPhoto;

    /**
     * 影厅简介
     */
    private String movieHallProfile;

    /**
     * 座位数
     */
    private Long seating;

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