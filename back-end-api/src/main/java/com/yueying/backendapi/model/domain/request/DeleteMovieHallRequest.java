package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 删除影厅请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class DeleteMovieHallRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -8581835952163042463L;

    /**
     * 影厅id
     */
    private Long movieHallId;
}
