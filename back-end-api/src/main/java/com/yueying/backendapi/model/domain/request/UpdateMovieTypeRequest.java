package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 修改活动信息请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class UpdateMovieTypeRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 8617147658992991505L;

    /**
     * 影片类型ID
     */
    private Integer movieTypeId;

    /**
     * 影片类型
     */
    private String movieTypeName;
}
