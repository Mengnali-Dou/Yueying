package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.mapper.EventMapper;
import com.yueying.backendapi.mapper.EventPriceMapper;
import com.yueying.backendapi.mapper.UserMapper;
import com.yueying.backendapi.model.domain.Event;
import com.yueying.backendapi.model.domain.EventOrder;
import com.yueying.backendapi.model.domain.EventPrice;
import com.yueying.backendapi.model.domain.User;
import com.yueying.backendapi.model.domain.request.*;
import com.yueying.backendapi.model.domain.response.ErrorResponseDto;
import com.yueying.backendapi.model.domain.response.EventOrderInfoDto;
import com.yueying.backendapi.model.domain.response.SuccessResponseDto;
import com.yueying.backendapi.service.EventOrderService;
import com.yueying.backendapi.mapper.EventOrderMapper;
import com.yueying.backendapi.utils.PublicMethods;
import com.yueying.backendapi.utils.ResponseData;
import com.yueying.backendapi.utils.UserPublicClass;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.yueying.backendapi.constant.EventConstant.*;
import static com.yueying.backendapi.constant.OrderConstant.*;
import static com.yueying.backendapi.constant.ResponseStatus.*;
import static com.yueying.backendapi.constant.UniversalConstant.*;
import static com.yueying.backendapi.constant.UserConstant.*;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_event_order(活动订单)】的数据库操作Service实现
* @createDate 2025-03-26 14:44:53
*/
@Service
public class EventOrderServiceImpl extends ServiceImpl<EventOrderMapper, EventOrder>
    implements EventOrderService{

    @Resource
    private EventOrderMapper eventOrderMapper;

    @Resource
    private EventMapper eventMapper;

    @Resource
    private EventPriceMapper eventPriceMapper;

    @Resource
    private UserMapper userMapper;

    private static EventMapper staticEventMapper;
    private static UserMapper staticUserMapper;

    @PostConstruct
    private void init() {
        staticEventMapper = eventMapper;
        staticUserMapper = userMapper;
    }

    @Override
    public ResponseEntity<Object> searchEventOrder(SearchEventOrderRequest searchEventOrderRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 是否登陆
        if (!UserPublicClass.isLogin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, USER_NOT_LOGGED_IN, errorResponseDto));
        }

        QueryWrapper<EventOrder> eventOrderQueryWrapper = new QueryWrapper<>();
        Object userInfo = httpServletRequest.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userInfo;

        // 是管理员权限可以使用用户id查询
        if (UserPublicClass.isAdmin(httpServletRequest) && searchEventOrderRequest.getUserId() > 0) {
            eventOrderQueryWrapper.eq("user_id", searchEventOrderRequest.getUserId());
        }

        // 普通用户权限，不使用用户id查询
        if (UserPublicClass.isAdmin(httpServletRequest)) {
            eventOrderQueryWrapper.eq("user_id", user.getUserId());
        }

        if (searchEventOrderRequest.getEventId() > 0) {
            // 活动是否存在
            QueryWrapper<Event> eventQueryWrapper = new QueryWrapper<>();
            eventQueryWrapper.eq("event_id", searchEventOrderRequest.getEventId());
            if (eventMapper.selectCount(eventQueryWrapper) <= 0) {
                return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, EVENT_NONENTITY, errorResponseDto));
            }
            eventOrderQueryWrapper.eq("event_id", searchEventOrderRequest.getEventId());
        }
        if (StringUtils.isNotBlank(searchEventOrderRequest.getBeginTime())) {
            eventOrderQueryWrapper.eq("begin_time", searchEventOrderRequest.getBeginTime());
        }
        if (searchEventOrderRequest.getOrderStatus() > 0) {
            eventOrderQueryWrapper.eq("order_status", searchEventOrderRequest.getOrderStatus());
        }

        return ResponseEntity.ok(ResponseData.responseData(OK, SEARCH_SUCCESSFULLY, convertToDtoList(eventOrderMapper.selectList(eventOrderQueryWrapper))));
    }

    @Override
    public ResponseEntity<Object> bookEvent(BookEventRequest bookEventRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 是否登陆
        if (!UserPublicClass.isLogin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, USER_NOT_LOGGED_IN, errorResponseDto));
        }

        // 必要参数是否为空
        if (!StringUtils.isNoneBlank(bookEventRequest.getSeat(), bookEventRequest.getSpectator(), bookEventRequest.getContact()) || bookEventRequest.getEventId() <= 0) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, PARAMETER_CANNOT_BE_NULL, errorResponseDto));
        }

        // 活动是否存在
        QueryWrapper<Event> eventQueryWrapper = new QueryWrapper<>();
        eventQueryWrapper.eq("event_id", bookEventRequest.getEventId());
        if (eventMapper.selectCount(eventQueryWrapper) <= 0) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, EVENT_NONENTITY, errorResponseDto));
        }

        // 座位类型是否存在
        QueryWrapper<EventPrice> eventPriceQueryWrapper = new QueryWrapper<>();
        eventPriceQueryWrapper.eq("event_id", bookEventRequest.getEventId());
        eventPriceQueryWrapper.eq("seat_type", bookEventRequest.getSeat());
        if (eventPriceMapper.selectCount(eventPriceQueryWrapper) <= 0) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, EVENT_PRICE_NONENTITY, errorResponseDto));
        }

        // 获取价格信息
        EventPrice eventPrice = eventPriceMapper.selectOne(eventPriceQueryWrapper);

        // 是否有余票
        if (eventPrice.getTicketsLeft() <= 0) {
            return ResponseEntity.status(CONFLICT).body(ResponseData.responseData(CONFLICT, INSUFFICIENT_BALANCE, errorResponseDto));
        }

        // 获取用户信息
        Object userObj = httpServletRequest.getSession().getAttribute(USER_LOGIN_STATE);
        User userInfo = (User) userObj;

        // 获取活动信息
        Event event = eventMapper.selectById(bookEventRequest.getEventId());

        // 下单
        EventOrder eventOrder = new EventOrder();
        eventOrder.setUserId(userInfo.getUserId());
        eventOrder.setEventId(bookEventRequest.getEventId());
        eventOrder.setSeat(bookEventRequest.getSeat());
        eventOrder.setBeginTime(event.getBeginTime());
        eventOrder.setContact(bookEventRequest.getContact());
        eventOrder.setSpectator(bookEventRequest.getSpectator());
        eventOrder.setOrderPrice(eventPrice.getPrice());
        boolean bookEvent = this.save(eventOrder);
        if (!bookEvent) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ResponseData.responseData(INTERNAL_SERVER_ERROR, BOOK_FAILED, errorResponseDto));
        }

        // 更新余票
        UpdateWrapper<EventPrice> eventPriceUpdateWrapper = new UpdateWrapper<>();
        eventPriceUpdateWrapper.eq("event_id", bookEventRequest.getEventId());
        eventPriceUpdateWrapper.eq("seat_type", bookEventRequest.getSeat());
        eventPriceUpdateWrapper.setSql("tickets_left = tickets_left - 1");
        eventPriceMapper.update(eventPriceUpdateWrapper);

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, BOOK_SUCCESSFULLY, successResponseDto));
    }

    @Override
    public ResponseEntity<Object> eventRefund(EventRefundRequest eventRefundRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 是否登陆
        if (!UserPublicClass.isLogin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, USER_NOT_LOGGED_IN, errorResponseDto));
        }

        // 订单是否存在
        QueryWrapper<EventOrder> eventOrderQueryWrapper = new QueryWrapper<>();
        eventOrderQueryWrapper.eq("order_id", eventRefundRequest.getEventOrderId());
        if (eventOrderMapper.selectCount(eventOrderQueryWrapper) <= 0) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, ORDER_NOT_FOUND, errorResponseDto));
        }

        // 订单是不是当前用户的
        EventOrder eventOrder = eventOrderMapper.selectById(eventRefundRequest.getEventOrderId());
        if (UserPublicClass.isCurrentUser(eventOrder.getUserId(), httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        // 退票
        EventOrder refundEventOrder = new EventOrder();
        refundEventOrder.setOrderId(eventOrder.getOrderId());
        refundEventOrder.setOrderStatus(ORDER_STATUS_REFUND_REQUEST);
        boolean refund = this.updateById(refundEventOrder);
        if (!refund) {
            return ResponseEntity.status(CONFLICT).body(ResponseData.responseData(CONFLICT, REFUND_FAILED, errorResponseDto));
        }

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, REFUND_SUCCESSFULLY, successResponseDto));
    }

    @Override
    public ResponseEntity<Object> eventRefundManage(EventRefundManageRequest eventRefundManageRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 验证权限
        if (UserPublicClass.isAdmin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        // 订单是否存在
        QueryWrapper<EventOrder> eventOrderQueryWrapper = new QueryWrapper<>();
        eventOrderQueryWrapper.eq("order_id", eventRefundManageRequest.getEventOrderId());
        if (eventOrderMapper.selectCount(eventOrderQueryWrapper) <= 0) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, ORDER_NOT_FOUND, errorResponseDto));
        }

        // 处理
        EventOrder eventOrder = new EventOrder();
        eventOrder.setOrderId(eventRefundManageRequest.getEventOrderId());
        eventOrder.setOrderStatus(eventRefundManageRequest.getAgree() ? ORDER_STATUS_REFUND_SUCCESSFUL : ORDER_STATUS_REFUND_REQUEST_FAILED);
        boolean manage = this.updateById(eventOrder);
        if (!manage) {
            return ResponseEntity.status(CONFLICT).body(ResponseData.responseData(CONFLICT, MANAGE_FAILED, errorResponseDto));
        }

        if (eventRefundManageRequest.getAgree()) {
            // 更新余票
            EventOrder eventOrderInfo = eventOrderMapper.selectById(eventRefundManageRequest.getEventOrderId());
            UpdateWrapper<EventPrice> eventPriceUpdateWrapper = new UpdateWrapper<>();
            eventPriceUpdateWrapper.eq("event_id", eventOrderInfo.getEventId());
            eventPriceUpdateWrapper.eq("seat_type", eventOrderInfo.getSeat());
            eventPriceUpdateWrapper.setSql("tickets_left = tickets_left + 1");
            eventPriceMapper.update(eventPriceUpdateWrapper);
        }

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, MANAGE_SUCCESSFULLY, successResponseDto));
    }

    @Override
    public ResponseEntity<Object> deleteEventOrder(DeleteEventOrderRequest deleteEventOrderRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 验证权限
        if (UserPublicClass.isAdmin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        // 订单是否存在
        QueryWrapper<EventOrder> eventOrderQueryWrapper = new QueryWrapper<>();
        eventOrderQueryWrapper.eq("order_id", deleteEventOrderRequest.getEventOrderId());
        if (eventOrderMapper.selectCount(eventOrderQueryWrapper) <= 0) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, ORDER_NOT_FOUND, errorResponseDto));
        }

        // 是否已退票
        EventOrder eventOrder = eventOrderMapper.selectById(deleteEventOrderRequest.getEventOrderId());
        if (!Objects.equals(eventOrder.getOrderStatus(), ORDER_STATUS_REFUND_SUCCESSFUL)) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, ORDER_UN_REFUND, errorResponseDto));
        }

        // 删除
        boolean deleted = this.removeById(deleteEventOrderRequest.getEventOrderId());
        if (!deleted) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ResponseData.responseData(INTERNAL_SERVER_ERROR, DELETE_FAILED, errorResponseDto));
        }

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, DELETE_SUCCESSFULLY, successResponseDto));
    }

    /**
     * 数据格式转换
     * @param eventOrders 活动订单数据库表字段列表
     * @return 活动订单信息列表
     */
    private List<EventOrderInfoDto> convertToDtoList(List<EventOrder> eventOrders) {
        return eventOrders.stream().map(EventOrderServiceImpl::convertToDto).collect(Collectors.toList());
    }

    /**
     * 数据格式转换
     * @param eventOrder 活动订单数据库表字段
     * @return 活动订单信息
     */
    private static EventOrderInfoDto convertToDto(EventOrder eventOrder) {
        EventOrderInfoDto eventOrderInfoDto = new EventOrderInfoDto();
        eventOrderInfoDto.setOrderId(eventOrder.getOrderId());
        eventOrderInfoDto.setUserId(eventOrder.getUserId());
        eventOrderInfoDto.setUserAccount(staticUserMapper.selectById(eventOrder.getUserId()).getUserAccount());
        eventOrderInfoDto.setEventId(eventOrder.getEventId());
        eventOrderInfoDto.setEventName(staticEventMapper.selectById(eventOrder.getEventId()).getEventName());
        eventOrderInfoDto.setSeat(eventOrder.getSeat());
        eventOrderInfoDto.setBeginTime(PublicMethods.dateTimeConvertToString(eventOrder.getBeginTime()));
        eventOrderInfoDto.setContact(eventOrder.getContact());
        eventOrderInfoDto.setSpectator(eventOrder.getSpectator());
        eventOrderInfoDto.setOrderPrice(eventOrder.getOrderPrice());
        eventOrderInfoDto.setOrderStatus(eventOrder.getOrderStatus());
        eventOrderInfoDto.setCreateTime(PublicMethods.dateTimeConvertToString(eventOrder.getCreateTime()));
        return eventOrderInfoDto;
    }
}




