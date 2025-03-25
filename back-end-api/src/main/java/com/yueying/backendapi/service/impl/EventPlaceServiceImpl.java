package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.model.domain.EventPlace;
import com.yueying.backendapi.model.domain.request.SearchEventPlaceRequest;
import com.yueying.backendapi.model.domain.response.EventPlaceInfoDto;
import com.yueying.backendapi.service.EventPlaceService;
import com.yueying.backendapi.mapper.EventPlaceMapper;
import com.yueying.backendapi.utils.ResponseData;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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




