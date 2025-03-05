package com.yueying.backendapi.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 影院管理员
 * @TableName tb_cinema_admin
 */
@Data
@TableName(value ="tb_cinema_admin")
public class CinemaAdmin implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long cinemaAdminId;

    /**
     * 影院id
     */
    private Long cinemaId;

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
    private Integer deleted;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}