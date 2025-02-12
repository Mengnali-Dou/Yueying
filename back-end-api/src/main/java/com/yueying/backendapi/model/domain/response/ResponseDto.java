package com.yueying.backendapi.model.domain.response;

import lombok.Data;

/**
 * 请求返回信息
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class ResponseDto<T> {

    /**
     * 状态码
     */
    private long status;

    /**
     * 请求返回消息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;
}
