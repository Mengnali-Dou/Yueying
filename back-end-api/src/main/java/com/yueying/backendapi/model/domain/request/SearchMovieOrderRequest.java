package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 搜索电影订单请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class SearchMovieOrderRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -6300292918982321211L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 开始日期
     */
    private String beginDate;

    /**
     * 订单状态
     */
    private Integer orderStatus;
}
