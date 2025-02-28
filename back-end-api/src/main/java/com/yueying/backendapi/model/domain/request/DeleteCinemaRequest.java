package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 删除影院请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class DeleteCinemaRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 3939481360588227486L;

    /**
     * id
     */
    private Long cinemaId;
}
