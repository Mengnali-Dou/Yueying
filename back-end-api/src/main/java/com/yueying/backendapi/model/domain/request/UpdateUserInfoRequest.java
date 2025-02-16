package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户信息修改请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class UpdateUserInfoRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -3717320337916839138L;

    /**
     * 用户ID
     */
    private long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 头像URL
     */
    private String avatarUrl;

    /**
     * 性别
     */
    private int gender;

    /**
     * 电话
     */
    private String phone;

    /**
     * email
     */
    private String email;
}
