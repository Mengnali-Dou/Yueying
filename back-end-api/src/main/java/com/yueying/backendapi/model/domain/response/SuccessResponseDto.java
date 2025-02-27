package com.yueying.backendapi.model.domain.response;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 请求成功消息dto
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class SuccessResponseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 4636098525646411220L;

    /**
     * 请求成功信息
     */
    private String message;
}
