package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 添加影院管理员请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class AddCinemaAdminRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 4952935016310611216L;

    /**
     * 影院id
     */
    private Long cinemaId;

    /**
     * 用户id
     */
    private Long userId;
}
