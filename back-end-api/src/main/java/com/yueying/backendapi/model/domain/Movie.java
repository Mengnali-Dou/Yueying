package com.yueying.backendapi.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 影片
 * @TableName tb_movie
 */
@TableName(value ="tb_movie")
@Data
public class Movie implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long movieId;

    /**
     * 影片名
     */
    private String movieName;

    /**
     * 影片类型id
     */
    private Integer movieTypeId;

    /**
     * 影片封面（小）
     */
    private String movieCoverSmall;

    /**
     * 影片封面（大）
     */
    private String movieCoverLarge;

    /**
     * 上映时间
     */
    private Date releaseDate;

    /**
     * 影片时长
     */
    private Date movieDuration;

    /**
     * 主要演员
     */
    private String mainActor;

    /**
     * 影片简介
     */
    private String movieProfile;

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