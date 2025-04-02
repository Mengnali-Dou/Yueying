package com.yueying.backendapi.model.domain.response;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 影片类型DTO
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class MovieTypeDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -2094101790691709023L;

    /**
     * id
     */
    private Integer movieTypeId;

    /**
     * 影片类型
     */
    private String movieType;
}
