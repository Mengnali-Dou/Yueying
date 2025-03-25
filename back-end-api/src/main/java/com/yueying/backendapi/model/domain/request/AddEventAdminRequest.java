package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 添加活动管理员请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class AddEventAdminRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -3020552498076550851L;

    /**
     * 活动id
     */
    private Long eventId;

    /**
     * 用户id
     */
    private Long userId;
}
