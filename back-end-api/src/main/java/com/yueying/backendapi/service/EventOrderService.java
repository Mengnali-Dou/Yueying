package com.yueying.backendapi.service;

import com.yueying.backendapi.model.domain.EventOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yueying.backendapi.model.domain.request.SearchEventOrderRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_event_order(活动订单)】的数据库操作Service
* @createDate 2025-03-26 14:44:53
*/
public interface EventOrderService extends IService<EventOrder> {

    /**
     * 搜索活动订单
     * @param searchEventOrderRequest 搜索活动订单请求体
     * @param httpServletRequest http请求信息
     * @return 活动订单列表
     */
    ResponseEntity<Object> searchEventOrder(SearchEventOrderRequest searchEventOrderRequest, HttpServletRequest httpServletRequest);
}
