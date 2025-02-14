package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户登录请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class UserLoginRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 5134958658491843150L;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String password;
}
