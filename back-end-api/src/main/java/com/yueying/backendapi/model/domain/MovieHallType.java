package com.yueying.backendapi.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 影厅类型
 * @TableName tb_movie_hall_type
 */
@Data
@TableName(value ="tb_movie_hall_type")
public class MovieHallType implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long typeId;

    /**
     * 类型名
     */
    private String typeName;

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