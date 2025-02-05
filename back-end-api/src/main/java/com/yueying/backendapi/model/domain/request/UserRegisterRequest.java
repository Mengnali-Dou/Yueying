package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户注册请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class UserRegisterRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1348473908854382236L;

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
    private String password;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 电话
     */
    private String phone;

    /**
     * email
     */
    private String email;
}
