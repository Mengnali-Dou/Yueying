package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 删除用户请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class DeleteUserRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -2804070353292978108L;

    /**
     * id
     */
    private long userId;
}
