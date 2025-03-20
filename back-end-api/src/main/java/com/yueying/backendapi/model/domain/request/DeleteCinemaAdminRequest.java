package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 删除影院管理员请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class DeleteCinemaAdminRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -4911516667556778253L;

    /**
     * id
     */
    private Long cinemaAdminId;
}
