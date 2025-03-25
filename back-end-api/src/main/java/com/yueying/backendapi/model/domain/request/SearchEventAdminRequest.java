package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 查询活动管理员请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class SearchEventAdminRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -626493987508757765L;

    /**
     * 活动id
     */
    private Long eventId;

    /**
     * 用户id
     */
    private Long userId;
}
