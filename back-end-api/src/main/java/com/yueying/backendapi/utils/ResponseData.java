package com.yueying.backendapi.utils;


import com.yueying.backendapi.model.domain.response.ResponseDto;

/**
 * 封装返回数据工具类
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
public class ResponseData {

    /**
     * 封装返回数据
     * @param status 状态码
     * @param msg 返回消息
     * @param data 返回数据
     * @return 封装后的数据
     */
    public static ResponseDto<Object> responseData(long status, String msg, Object data) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        responseDto.setStatus(status);
        responseDto.setMessage(msg);
        responseDto.setData(data);
        return responseDto;
    }
}
