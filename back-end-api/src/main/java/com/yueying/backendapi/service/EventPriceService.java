package com.yueying.backendapi.service;

import com.yueying.backendapi.model.domain.EventPrice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yueying.backendapi.model.domain.request.AddEventPriceRequest;
import com.yueying.backendapi.model.domain.request.SearchEventPriceRequest;
import com.yueying.backendapi.model.domain.request.UpdateEventPriceRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_event_price(活动票价)】的数据库操作Service
* @createDate 2025-03-25 21:22:41
*/
public interface EventPriceService extends IService<EventPrice> {

    /**
     * 搜索活动票价
     * @param searchEventPriceRequest 搜索活动票价请求体
     * @return 活动票价列表
     */
    ResponseEntity<Object> searchEventPrice(SearchEventPriceRequest searchEventPriceRequest);

    /**
     * 添加活动票价
     * @param addEventPriceRequest 添加活动票价请求体
     * @param httpServletRequest http请求信息
     * @return 是否添加成功
     */
    ResponseEntity<Object> addEventPrice(AddEventPriceRequest addEventPriceRequest, HttpServletRequest httpServletRequest);

    /**
     * 修改活动票价信息
     * @param updateEventPriceRequest 修改活动票价信息请求体
     * @param httpServletRequest http请求信息
     * @return 是否修改成功
     */
    ResponseEntity<Object> updateEventPrice(UpdateEventPriceRequest updateEventPriceRequest, HttpServletRequest httpServletRequest);
}
