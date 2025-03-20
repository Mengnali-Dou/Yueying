package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 添加影院类型请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class AddMovieHallTypeRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 2381243235805751289L;

    /**
     * 影厅类型名
     */
    private String movieHallTypeName;
}
