package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.mapper.EventMapper;
import com.yueying.backendapi.mapper.UserMapper;
import com.yueying.backendapi.model.domain.Event;
import com.yueying.backendapi.model.domain.EventOrder;
import com.yueying.backendapi.model.domain.User;
import com.yueying.backendapi.model.domain.request.SearchEventOrderRequest;
import com.yueying.backendapi.model.domain.response.ErrorResponseDto;
import com.yueying.backendapi.model.domain.response.EventOrderInfoDto;
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
import java.util.stream.Collectors;

import static com.yueying.backendapi.constant.EventConstant.*;
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




