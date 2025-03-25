package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.mapper.EventAdminMapper;
import com.yueying.backendapi.mapper.EventMapper;
import com.yueying.backendapi.model.domain.Event;
import com.yueying.backendapi.model.domain.EventAdmin;
import com.yueying.backendapi.model.domain.EventPrice;
import com.yueying.backendapi.model.domain.request.AddEventPriceRequest;
import com.yueying.backendapi.model.domain.request.SearchEventPriceRequest;
import com.yueying.backendapi.model.domain.response.ErrorResponseDto;
import com.yueying.backendapi.model.domain.response.EventPriceInfoDto;
import com.yueying.backendapi.model.domain.response.SuccessResponseDto;
import com.yueying.backendapi.service.EventPriceService;
import com.yueying.backendapi.mapper.EventPriceMapper;
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

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_event_price(活动票价)】的数据库操作Service实现
* @createDate 2025-03-25 21:22:41
*/
@Service
public class EventPriceServiceImpl extends ServiceImpl<EventPriceMapper, EventPrice>
    implements EventPriceService{

    @Resource
    private EventPriceMapper eventPriceMapper;

    @Resource
    private EventMapper eventMapper;

    @Resource
    private EventAdminMapper eventAdminMapper;

    private static EventMapper staticEventMapper;

    @PostConstruct
    private void init() {
        staticEventMapper = eventMapper;
    }

    @Override
    public ResponseEntity<Object> searchEventPrice(SearchEventPriceRequest searchEventPriceRequest) {

        QueryWrapper<EventPrice> eventPriceQueryWrapper = new QueryWrapper<>();

        // 活动是否存在
        if (searchEventPriceRequest.getEventId() > 0) {
            eventPriceQueryWrapper.eq("event_id", searchEventPriceRequest.getEventId());
        }

        return ResponseEntity.ok(ResponseData.responseData(OK, SEARCH_SUCCESSFULLY, convertToDtoList(eventPriceMapper.selectList(eventPriceQueryWrapper))));
    }

    @Override
    public ResponseEntity<Object> addEventPrice(AddEventPriceRequest addEventPriceRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 必要参数是否为空
        if (!StringUtils.isNoneBlank(addEventPriceRequest.getSeatType()) || addEventPriceRequest.getEventId() <= 0 || addEventPriceRequest.getSeatRows() <= 0 || addEventPriceRequest.getSeatCols() <= 0 || addEventPriceRequest.getPrice() <= 0) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, PARAMETER_CANNOT_BE_NULL, errorResponseDto));
        }

        // 验证权限
        if (UserPublicClass.isAdmin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }
        QueryWrapper<EventAdmin> eventAdminQueryWrapper = new QueryWrapper<>();
        eventAdminQueryWrapper.eq("event_id", addEventPriceRequest.getEventId());
        eventAdminQueryWrapper.eq("user_id", UserPublicClass.getUserId(httpServletRequest));
        if (eventAdminMapper.selectCount(eventAdminQueryWrapper) <= 0) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        // 活动是否存在
        QueryWrapper<Event> eventQueryWrapper = new QueryWrapper<>();
        eventQueryWrapper.eq("event_id", addEventPriceRequest.getEventId());
        if (eventMapper.selectCount(eventQueryWrapper) <= 0) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, EVENT_NONENTITY, errorResponseDto));
        }

        // 活动票价是否存在
        QueryWrapper<EventPrice> eventPriceQueryWrapper = new QueryWrapper<>();
        eventPriceQueryWrapper.eq("event_id", addEventPriceRequest.getEventId());
        eventPriceQueryWrapper.eq("seat_type", addEventPriceRequest.getSeatType());
        if (eventPriceMapper.selectCount(eventPriceQueryWrapper) > 0) {
            return ResponseEntity.status(CONFLICT).body(ResponseData.responseData(CONFLICT, EVENT_PRICE_HAS_ADDED, errorResponseDto));
        }

        // 添加
        EventPrice eventPrice = new EventPrice();
        eventPrice.setEventId(addEventPriceRequest.getEventId());
        eventPrice.setSeatType(addEventPriceRequest.getSeatType());
        eventPrice.setSeatRows(addEventPriceRequest.getSeatRows());
        eventPrice.setSeatCols(addEventPriceRequest.getSeatCols());
        eventPrice.setPrice(addEventPriceRequest.getPrice());
        eventPrice.setTicketsLeft((long) addEventPriceRequest.getSeatCols() * addEventPriceRequest.getSeatRows());
        eventPrice.setTotalNum((long) addEventPriceRequest.getSeatCols() * addEventPriceRequest.getSeatRows());
        boolean addEventPrice = this.save(eventPrice);
        if (!addEventPrice) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ResponseData.responseData(INTERNAL_SERVER_ERROR, FAILED_TO_INSERT, errorResponseDto));
        }

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, INSERT_SUCCESSFULLY, successResponseDto));
    }

    /**
     * 数据格式转换
     * @param eventPrices 活动票价数据库表字段列表
     * @return 活动票价信息列表
     */
    private List<EventPriceInfoDto> convertToDtoList(List<EventPrice> eventPrices) {
        return eventPrices.stream().map(EventPriceServiceImpl::convertToDto).collect(Collectors.toList());
    }

    /**
     * 数据格式转换
     * @param eventPrice 活动票价数据库表字段
     * @return 活动票价信息
     */
    private static EventPriceInfoDto convertToDto(EventPrice eventPrice) {
        EventPriceInfoDto eventPriceInfoDto = new EventPriceInfoDto();
        eventPriceInfoDto.setPriceId(eventPrice.getPriceId());
        eventPriceInfoDto.setEventId(eventPrice.getEventId());
        eventPriceInfoDto.setEventName(staticEventMapper.selectById(eventPrice.getEventId()).getEventName());
        eventPriceInfoDto.setSeatType(eventPrice.getSeatType());
        eventPriceInfoDto.setSeatRows(eventPrice.getSeatRows());
        eventPriceInfoDto.setSeatCols(eventPrice.getSeatCols());
        eventPriceInfoDto.setPrice(eventPrice.getPrice());
        eventPriceInfoDto.setTicketsLeft(eventPrice.getTicketsLeft());
        eventPriceInfoDto.setTotalNum(eventPrice.getTotalNum());
        return eventPriceInfoDto;
    }
}




