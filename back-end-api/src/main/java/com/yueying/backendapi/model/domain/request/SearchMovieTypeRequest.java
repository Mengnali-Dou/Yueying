package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 查询影片类型请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class SearchMovieTypeRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 6840625006978629093L;

    /**
     * 影片类型id
     */
    private Integer movieTypeId;
}
