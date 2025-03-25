package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.mapper.EventPlaceMapper;
import com.yueying.backendapi.model.domain.Event;
import com.yueying.backendapi.model.domain.request.SearchEventRequest;
import com.yueying.backendapi.model.domain.response.EventInfoDto;
import com.yueying.backendapi.service.EventService;
import com.yueying.backendapi.mapper.EventMapper;
import com.yueying.backendapi.utils.PublicMethods;
import com.yueying.backendapi.utils.ResponseData;
import jakarta.annotation.PostConstruct;
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




