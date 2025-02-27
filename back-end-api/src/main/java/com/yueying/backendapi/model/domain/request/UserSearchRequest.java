package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 搜索用户请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class UserSearchRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 199596878240951886L;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户名
     */
    private String userName;
}
