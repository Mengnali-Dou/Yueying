package com.yueying.backendapi.service;

import com.yueying.backendapi.model.domain.EventOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yueying.backendapi.model.domain.request.*;
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

    /**
     * 活动下单
     * @param bookEventRequest 活动下单请求体
     * @param httpServletRequest http请求信息
     * @return 是否预定成功
     */
    ResponseEntity<Object> bookEvent(BookEventRequest bookEventRequest, HttpServletRequest httpServletRequest);

    /**
     * 活动退票申请
     * @param eventRefundRequest 活动退票申请请求体
     * @param httpServletRequest http请求信息
     * @return 退票申请是否成功
     */
    ResponseEntity<Object> eventRefund(EventRefundRequest eventRefundRequest, HttpServletRequest httpServletRequest);

    /**
     * 活动退票申请处理
     * @param eventRefundManageRequest 活动退票申请处理请求体
     * @param httpServletRequest http请求信息
     * @return 是否处理成功
     */
    ResponseEntity<Object> eventRefundManage(EventRefundManageRequest eventRefundManageRequest, HttpServletRequest httpServletRequest);

    /**
     * 活动订单删除
     * @param deleteEventOrderRequest 活动订单删除请求体
     * @param httpServletRequest http请求信息
     * @return 是否删除成功
     */
    ResponseEntity<Object> deleteEventOrder(DeleteEventOrderRequest deleteEventOrderRequest, HttpServletRequest httpServletRequest);
}
