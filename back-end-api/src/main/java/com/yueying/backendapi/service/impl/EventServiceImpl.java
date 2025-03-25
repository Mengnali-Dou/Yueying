package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.mapper.EventAdminMapper;
import com.yueying.backendapi.mapper.EventPlaceMapper;
import com.yueying.backendapi.model.domain.Event;
import com.yueying.backendapi.model.domain.EventAdmin;
import com.yueying.backendapi.model.domain.EventPlace;
import com.yueying.backendapi.model.domain.request.AddEventRequest;
import com.yueying.backendapi.model.domain.request.SearchEventRequest;
import com.yueying.backendapi.model.domain.request.UpdateEventRequest;
import com.yueying.backendapi.model.domain.response.ErrorResponseDto;
import com.yueying.backendapi.model.domain.response.EventInfoDto;
import com.yueying.backendapi.model.domain.response.SuccessResponseDto;
import com.yueying.backendapi.service.EventService;
import com.yueying.backendapi.mapper.EventMapper;
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
import static com.yueying.backendapi.constant.EventPlaceMessage.*;
import static com.yueying.backendapi.constant.ResponseStatus.*;
import static com.yueying.backendapi.constant.UniversalConstant.*;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_event(活动)】的数据库操作Service实现
* @createDate 2025-03-25 10:48:32
*/
@Service
public class EventServiceImpl extends ServiceImpl<EventMapper, Event>
    implements EventService{

    @Resource
    private EventMapper eventMapper;

    @Resource
    private EventPlaceMapper eventPlaceMapper;

    @Resource
    private EventAdminMapper eventAdminMapper;

    private static EventPlaceMapper staticEventPlaceMapper;

    @PostConstruct
    private void init() {
        staticEventPlaceMapper = eventPlaceMapper;
    }

    @Override
    public ResponseEntity<Object> searchEvent(SearchEventRequest searchEventRequest) {

        QueryWrapper<Event> eventQueryWrapper = new QueryWrapper<>();

        // 活动场地名已输入判断是否存在
        if (searchEventRequest.getEventPlaceId() > 0) {
            eventQueryWrapper.like("event_place_id", searchEventRequest.getEventPlaceId());
        }

        if (StringUtils.isNotBlank(searchEventRequest.getEventName())) {
            eventQueryWrapper.like("event_name", searchEventRequest.getEventName());
        }
        if (StringUtils.isNotBlank(searchEventRequest.getEventType())) {
            eventQueryWrapper.like("event_type", searchEventRequest.getEventType());
        }

        return ResponseEntity.ok(ResponseData.responseData(OK, SEARCH_SUCCESSFULLY, convertToDtoList(eventMapper.selectList(eventQueryWrapper))));
    }

    @Override
    public ResponseEntity<Object> addEvent(AddEventRequest addEventRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 必要参数是否为空
        if (!StringUtils.isNoneBlank(addEventRequest.getEventName(), addEventRequest.getEventType(), addEventRequest.getBeginTime(), addEventRequest.getEndTime()) || addEventRequest.getEventPlaceId() <= 0) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, PARAMETER_CANNOT_BE_NULL, errorResponseDto));
        }

        // 验证权限
        if (UserPublicClass.isAdmin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        // 活动场地是否存在
        QueryWrapper<EventPlace> eventPlaceQueryWrapper = new QueryWrapper<>();
        eventPlaceQueryWrapper.eq("place_id", addEventRequest.getEventPlaceId());
        if (eventPlaceMapper.selectCount(eventPlaceQueryWrapper) <= 0) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, EVENT_PLACE_NONENTITY, errorResponseDto));
        }

        // 添加活动场地
        Event event = new Event();
        event.setEventName(addEventRequest.getEventName());
        event.setEventPlaceId(addEventRequest.getEventPlaceId());
        event.setEventType(addEventRequest.getEventType());
        event.setMainActor(addEventRequest.getMainActor());
        event.setEventCoverSmall(addEventRequest.getEventCoverSmall());
        event.setEventCoverLarge(addEventRequest.getEventCoverLarge());
        event.setBeginTime(PublicMethods.stringConvertToDateTime(addEventRequest.getBeginTime()));
        event.setFinishTime(PublicMethods.stringConvertToDateTime(addEventRequest.getEndTime()));
        event.setFinished(EVENT_STATUS_UNFINISHED);
        event.setEventProfile(addEventRequest.getEventProfile());

        boolean addEvent = this.save(event);
        if (!addEvent) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ResponseData.responseData(INTERNAL_SERVER_ERROR, FAILED_TO_INSERT, errorResponseDto));
        }

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, INSERT_SUCCESSFULLY, successResponseDto));
    }

    @Override
    public ResponseEntity<Object> updateEvent(UpdateEventRequest updateEventRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 必要参数是否为空
        if (updateEventRequest.getEventId() <= 0) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, PARAMETER_CANNOT_BE_NULL, errorResponseDto));
        }

        // 验证权限
        if (UserPublicClass.isAdmin(httpServletRequest) || UserPublicClass.isEventAdmin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }
        QueryWrapper<EventAdmin> eventAdminQueryWrapper = new QueryWrapper<>();
        eventAdminQueryWrapper.eq("event_id", updateEventRequest.getEventId());
        eventAdminQueryWrapper.eq("user_id", UserPublicClass.getUserId(httpServletRequest));
        if (eventAdminMapper.selectCount(eventAdminQueryWrapper) <= 0) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        // 是否存在
        QueryWrapper<Event> eventQueryWrapper = new QueryWrapper<>();
        eventQueryWrapper.eq("event_id", updateEventRequest.getEventId());
        if (eventMapper.selectCount(eventQueryWrapper) <= 0) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, EVENT_NONENTITY, errorResponseDto));
        }

        Event event = new Event();
        event.setEventId(updateEventRequest.getEventId());
        if (StringUtils.isNotBlank(updateEventRequest.getEventName())) {
            event.setEventName(updateEventRequest.getEventName());
        }
        if (updateEventRequest.getEventPlaceId() > 0) {
            QueryWrapper<EventPlace> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("place_id", updateEventRequest.getEventPlaceId());
            if (eventPlaceMapper.selectCount(queryWrapper) <= 0) {
                return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, EVENT_PLACE_NONENTITY, errorResponseDto));
            }
            event.setEventPlaceId(updateEventRequest.getEventPlaceId());
        }
        if (StringUtils.isNotBlank(updateEventRequest.getEventType())) {
            event.setEventType(updateEventRequest.getEventType());
        }
        if (StringUtils.isNotBlank(updateEventRequest.getMainActor())) {
            event.setMainActor(updateEventRequest.getMainActor());
        }
        if (StringUtils.isNotBlank(updateEventRequest.getEventCoverSmall())) {
            event.setEventCoverSmall(updateEventRequest.getEventCoverSmall());
        }
        if (StringUtils.isNotBlank(updateEventRequest.getEventCoverLarge())) {
            event.setEventCoverLarge(updateEventRequest.getEventCoverLarge());
        }
        if (StringUtils.isNotBlank(updateEventRequest.getBeginDateTime())) {
            event.setBeginTime(PublicMethods.stringConvertToDateTime(updateEventRequest.getBeginDateTime()));
        }
        if (StringUtils.isNotBlank(updateEventRequest.getEndDateTime())) {
            event.setFinishTime(PublicMethods.stringConvertToDateTime(updateEventRequest.getEndDateTime()));
        }
        if (StringUtils.isNotBlank(updateEventRequest.getEventProfile())) {
            event.setEventProfile(updateEventRequest.getEventProfile());
        }
        eventMapper.updateById(event);

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, UPDATE_SUCCESSFULLY, successResponseDto));
    }

    /**
     * 数据格式转换
     * @param events 数据库表字段列表
     * @return 活动信息列表
     */
    private List<EventInfoDto> convertToDtoList(List<Event> events) {
        return events.stream().map(EventServiceImpl::convertToDto).collect(Collectors.toList());
    }

    /**
     * 数据格式转换
     * @param event 活动数据库表字段
     * @return 活动信息
     */
    private static EventInfoDto convertToDto(Event event) {
        EventInfoDto eventInfoDto = new EventInfoDto();
        eventInfoDto.setEventId(event.getEventId());
        eventInfoDto.setEventPlaceId(event.getEventPlaceId());
        eventInfoDto.setEventPlaceName(staticEventPlaceMapper.selectById(event.getEventPlaceId()).getPlaceName());
        eventInfoDto.setEventName(event.getEventName());
        eventInfoDto.setEventType(event.getEventType());
        eventInfoDto.setMainActor(event.getMainActor());
        eventInfoDto.setEventCoverSmall(event.getEventCoverSmall());
        eventInfoDto.setEventCoverLarge(event.getEventCoverLarge());
        eventInfoDto.setBeginDate(PublicMethods.dateTimeConvertToString(event.getBeginTime()));
        eventInfoDto.setEndDate(PublicMethods.dateTimeConvertToString(event.getFinishTime()));
        eventInfoDto.setFinished(event.getFinished() != 0);
        eventInfoDto.setEventProfile(event.getEventProfile());
        return eventInfoDto;
    }
}




