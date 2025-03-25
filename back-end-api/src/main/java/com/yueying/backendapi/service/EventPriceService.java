package com.yueying.backendapi.service;

import com.yueying.backendapi.model.domain.EventPrice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yueying.backendapi.model.domain.request.SearchEventPriceRequest;
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
}
