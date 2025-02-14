package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 退出登录请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class UserLogoutRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 7167972230464065150L;

    /**
     * id
     */
    private long userId;
}
