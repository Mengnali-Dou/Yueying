package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 修改密码请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class PasswordResetRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 997665870152050712L;

    /**
     * 用户ID
     */
    private long userId;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 旧密码
     */
    private String oldPassword;
}
