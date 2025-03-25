package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.mapper.EventMapper;
import com.yueying.backendapi.model.domain.EventPrice;
import com.yueying.backendapi.model.domain.request.SearchEventPriceRequest;
import com.yueying.backendapi.model.domain.response.EventPriceInfoDto;
import com.yueying.backendapi.service.EventPriceService;
import com.yueying.backendapi.mapper.EventPriceMapper;
import com.yueying.backendapi.utils.ResponseData;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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




