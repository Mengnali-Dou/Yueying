package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 查询影院管理员请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class SearchCinemaAdminRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 8498151347017179639L;

    /**
     * 影院id
     */
    private Long cinemaId;

    /**
     * 用户id
     */
    private Long userId;
}
