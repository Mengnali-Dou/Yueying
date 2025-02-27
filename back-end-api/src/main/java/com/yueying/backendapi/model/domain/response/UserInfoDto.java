package com.yueying.backendapi.model.domain.response;

import lombok.Data;

import java.util.Date;

/**
 * 用户信息Dto
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class UserInfoDto {

    /**
     * id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 性别
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
     * 创建时间
     */
    private Date createTime;

    /**
     * 用户角色
     */
    private int userRole;
}
