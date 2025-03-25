package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.model.domain.EventPlace;
import com.yueying.backendapi.model.domain.request.AddEventPlaceRequest;
import com.yueying.backendapi.model.domain.request.SearchEventPlaceRequest;
import com.yueying.backendapi.model.domain.request.UpdateEventPlaceInfoRequest;
import com.yueying.backendapi.model.domain.response.ErrorResponseDto;
import com.yueying.backendapi.model.domain.response.EventPlaceInfoDto;
import com.yueying.backendapi.model.domain.response.SuccessResponseDto;
import com.yueying.backendapi.service.EventPlaceService;
import com.yueying.backendapi.mapper.EventPlaceMapper;
import com.yueying.backendapi.utils.ResponseData;
import com.yueying.backendapi.utils.UserPublicClass;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.yueying.backendapi.constant.EventPlaceMessage.*;
import static com.yueying.backendapi.constant.ResponseStatus.*;
import static com.yueying.backendapi.constant.UniversalConstant.*;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_event_place(活动场地)】的数据库操作Service实现
* @createDate 2025-03-25 11:08:42
*/
@Service
public class EventPlaceServiceImpl extends ServiceImpl<EventPlaceMapper, EventPlace>
    implements EventPlaceService{

    @Resource
    private EventPlaceMapper eventPlaceMapper;

    @Override
    public ResponseEntity<Object> searchEventPlace(SearchEventPlaceRequest searchEventPlaceRequest) {

        QueryWrapper<EventPlace> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(searchEventPlaceRequest.getPlaceName())) {
            queryWrapper.like("place_name", searchEventPlaceRequest.getPlaceName());
        }
        if (StringUtils.isNotBlank(searchEventPlaceRequest.getPlaceType())) {
            queryWrapper.like("place_type", searchEventPlaceRequest.getPlaceType());
        }

        return ResponseEntity.ok(ResponseData.responseData(OK, SEARCH_SUCCESSFULLY, convertToDtoList(eventPlaceMapper.selectList(queryWrapper))));
    }

    @Override
    public ResponseEntity<Object> addEventPlace(AddEventPlaceRequest addEventPlaceRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 参数是否为空
        if (!StringUtils.isNoneBlank(addEventPlaceRequest.getPlaceAddress(), addEventPlaceRequest.getPlaceType(), addEventPlaceRequest.getPlaceName()) || addEventPlaceRequest.getMaxSeats() <= 0) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, PARAMETER_CANNOT_BE_NULL, errorResponseDto));
        }

        // 验证权限
        if (UserPublicClass.isAdmin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        // 场地是否存在
        QueryWrapper<EventPlace> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("place_name", addEventPlaceRequest.getPlaceName());
        long count = eventPlaceMapper.selectCount(queryWrapper);
        if (count > 0) {
            return ResponseEntity.status(CONFLICT).body(ResponseData.responseData(CONFLICT, EVENT_PLACE_HAS_ADDED, errorResponseDto));
        }

        // 添加
        EventPlace eventPlace = new EventPlace();
        eventPlace.setPlaceName(addEventPlaceRequest.getPlaceName());
        eventPlace.setPlaceType(addEventPlaceRequest.getPlaceType());
        eventPlace.setPlaceAddress(addEventPlaceRequest.getPlaceAddress());
        eventPlace.setMaxSeats(addEventPlaceRequest.getMaxSeats());
        boolean addEventPlace = this.save(eventPlace);
        if (!addEventPlace) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ResponseData.responseData(INTERNAL_SERVER_ERROR, FAILED_TO_INSERT, errorResponseDto));
        }

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, INSERT_SUCCESSFULLY, successResponseDto));
    }

    @Override
    public ResponseEntity<Object> updateEventPlace(UpdateEventPlaceInfoRequest updateEventPlaceInfoRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 必要参数是否为空
        if (updateEventPlaceInfoRequest.getPlaceId() <= 0) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, PARAMETER_CANNOT_BE_NULL, errorResponseDto));
        }

        // 验证权限
        if (UserPublicClass.isAdmin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        // 是否存在
        QueryWrapper<EventPlace> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("place_id", updateEventPlaceInfoRequest.getPlaceId());
        if (eventPlaceMapper.selectCount(queryWrapper) <= 0) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, EVENT_PLACE_NONENTITY, errorResponseDto));
        }

        // 修改
        EventPlace eventPlace = new EventPlace();
        eventPlace.setPlaceId(updateEventPlaceInfoRequest.getPlaceId());
        if (StringUtils.isNotBlank(updateEventPlaceInfoRequest.getPlaceName())) {
            eventPlace.setPlaceName(updateEventPlaceInfoRequest.getPlaceName());
        }
        if (StringUtils.isNotBlank(updateEventPlaceInfoRequest.getPlaceAddress())) {
            eventPlace.setPlaceAddress(updateEventPlaceInfoRequest.getPlaceAddress());
        }
        if (StringUtils.isNotBlank(updateEventPlaceInfoRequest.getPlaceType())) {
            eventPlace.setPlaceType(updateEventPlaceInfoRequest.getPlaceType());
        }
        if (updateEventPlaceInfoRequest.getMaxSeats() > 0) {
            eventPlace.setMaxSeats(updateEventPlaceInfoRequest.getMaxSeats());
        }
        eventPlaceMapper.updateById(eventPlace);

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, UPDATE_SUCCESSFULLY, successResponseDto));
    }

    /**
     * 数据格式转换
     * @param eventPlaces 活动场地数据库表字段列表
     * @return 活动场地信息DTO列表
     */
    private List<EventPlaceInfoDto> convertToDtoList(List<EventPlace> eventPlaces) {
        return eventPlaces.stream().map(EventPlaceServiceImpl::convertToDto).collect(Collectors.toList());
    }

    /**
     * 数据格式转换
     * @param eventPlace 活动场地数据库表字段
     * @return 活动场地信息
     */
    private static EventPlaceInfoDto convertToDto(EventPlace eventPlace) {
        EventPlaceInfoDto eventPlaceDto = new EventPlaceInfoDto();
        eventPlaceDto.setPlaceId(eventPlace.getPlaceId());
        eventPlaceDto.setPlaceName(eventPlace.getPlaceName());
        eventPlaceDto.setPlaceType(eventPlace.getPlaceType());
        eventPlaceDto.setPlaceAddress(eventPlace.getPlaceAddress());
        eventPlaceDto.setMaxSeats(eventPlace.getMaxSeats());
        return eventPlaceDto;
    }
}




