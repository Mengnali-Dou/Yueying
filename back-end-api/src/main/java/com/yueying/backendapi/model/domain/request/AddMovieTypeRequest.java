package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 添加影片类型请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class AddMovieTypeRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 7839842449282330496L;

    /**
     * 影片类型
     */
    private String movieTypeName;
}
