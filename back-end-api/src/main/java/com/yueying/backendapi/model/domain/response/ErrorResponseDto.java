package com.yueying.backendapi.model.domain.response;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 请求错误信息DTO
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class ErrorResponseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 4548658801498605027L;

    /**
     * 错误信息
     */
    private String message;
}
