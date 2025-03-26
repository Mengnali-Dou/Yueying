package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 修改活动管理员请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class UpdateEventAdminRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 4675187332587248623L;

    /**
     * 活动管理员id
     */
    private Long eventAdminId;

    /**
     * 活动id
     */
    private Long eventId;

    /**
     * 用户id
     */
    private Long userId;
}
