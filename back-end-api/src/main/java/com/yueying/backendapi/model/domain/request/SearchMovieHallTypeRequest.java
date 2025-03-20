package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 搜索影厅类型请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class SearchMovieHallTypeRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 554255835826338329L;

    /**
     * 影厅类型id
     */
    private Long typeId;
}
