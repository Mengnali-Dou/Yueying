package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 删除影厅类型请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class DeleteMovieHallTypeRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 3482753711555081220L;

    /**
     * 影片id
     */
    private Long movieHallTypeId;
}
