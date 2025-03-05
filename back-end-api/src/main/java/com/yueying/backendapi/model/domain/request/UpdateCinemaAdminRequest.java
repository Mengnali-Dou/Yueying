package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 影院管理员修改请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class UpdateCinemaAdminRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -4259257898549420117L;

    /**
     * 影院管理员id
     */
    private Long cinemaAdminId;

    /**
     * 影院id
     */
    private Long cinemaId;

    /**
     * 用户id
     */
    private Long userId;
}
