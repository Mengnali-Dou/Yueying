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
 * 用户表
 * @TableName tb_user
 */
@TableName(value = "tb_user")
@Data
public class User implements Serializable {
    /**
     * id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 用户角色 0 - 普通用户 1 - 客服 2 - 影院管理员 3 - 活动管理员 4 - 系统管理员 5 - 超级管理员
     */
    private Integer userRole;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 性别 0 - 男 1 - 女
     */
    private Integer gender;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态 0 - 正常
     */
    private Integer userStatus;

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