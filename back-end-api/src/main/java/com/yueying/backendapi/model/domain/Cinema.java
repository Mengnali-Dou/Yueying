package com.yueying.backendapi.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 影院
 * @TableName tb_cinema
 */
@Data
@TableName(value ="tb_cinema")
public class Cinema implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long cinemaId;

    /**
     * 影院名
     */
    private String cinemaName;

    /**
     * 影院地址
     */
    private String cinemaAddress;

    /**
     * 影院简介
     */
    private String cinemaProfile;

    /**
     * 影院服务
     */
    private String cinemaService;

    /**
     * 影院电话
     */
    private String cinemaPhone;

    /**
     * 影院交通
     */
    private String cinemaTraffic;

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