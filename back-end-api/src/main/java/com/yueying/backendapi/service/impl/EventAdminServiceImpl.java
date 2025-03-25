package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.mapper.EventMapper;
import com.yueying.backendapi.mapper.UserMapper;
import com.yueying.backendapi.model.domain.Event;
import com.yueying.backendapi.model.domain.EventAdmin;
import com.yueying.backendapi.model.domain.User;
import com.yueying.backendapi.model.domain.request.SearchEventAdminRequest;
import com.yueying.backendapi.model.domain.response.ErrorResponseDto;
import com.yueying.backendapi.model.domain.response.EventAdminDto;
import com.yueying.backendapi.service.EventAdminService;
import com.yueying.backendapi.mapper.EventAdminMapper;
import com.yueying.backendapi.utils.ResponseData;
import com.yueying.backendapi.utils.UserPublicClass;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.yueying.backendapi.constant.EventConstant.*;
import static com.yueying.backendapi.constant.ResponseStatus.*;
import static com.yueying.backendapi.constant.UniversalConstant.*;
import static com.yueying.backendapi.constant.UserConstant.USER_DOES_NOT_EXISTS;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_event_admin(活动管理员)】的数据库操作Service实现
* @createDate 2025-03-23 18:43:19
*/
@Service
public class EventAdminServiceImpl extends ServiceImpl<EventAdminMapper, EventAdmin>
    implements EventAdminService{

    @Resource
    private EventAdminMapper eventAdminMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private EventMapper eventMapper;

    private static UserMapper staticUserMapper;
    private static EventMapper staticEventMapper;

    @PostConstruct
    private void init() {
        staticUserMapper = userMapper;
        staticEventMapper = eventMapper;
    }

    @Override
    public ResponseEntity<Object> searchEventAdmin(SearchEventAdminRequest searchEventAdminRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 验证权限
        if (UserPublicClass.isAdmin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        QueryWrapper<EventAdmin> eventAdminQueryWrapper = new QueryWrapper<>();

        // 活动是否存在（如果有该参数）
        if (searchEventAdminRequest.getEventId() > 0) {
            QueryWrapper<Event> eventQueryWrapper = new QueryWrapper<>();
            eventQueryWrapper.eq("event_id", searchEventAdminRequest.getEventId());
            if (eventMapper.selectCount(eventQueryWrapper) <= 0) {
                return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, EVENT_NONENTITY, errorResponseDto));
            }
            eventAdminQueryWrapper.eq("event_id", searchEventAdminRequest.getEventId());
        }

        // 用户是否存在（如果有该参数）
        if (searchEventAdminRequest.getUserId() > 0) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", searchEventAdminRequest.getUserId());
            if (userMapper.selectCount(queryWrapper) <= 0) {
                return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, USER_DOES_NOT_EXISTS, errorResponseDto));
            }
            eventAdminQueryWrapper.eq("user_id", searchEventAdminRequest.getUserId());
        }

        return ResponseEntity.ok(ResponseData.responseData(OK, SEARCH_SUCCESSFULLY, convertToDtoList(eventAdminMapper.selectList(eventAdminQueryWrapper))));
    }

    /**
     * 数据格式转换
     * @param eventAdminList 活动管理员数据库表字段列表
     * @return 活动管理员信息列表
     */
    private List<EventAdminDto> convertToDtoList(List<EventAdmin> eventAdminList) {
        return eventAdminList.stream().map(EventAdminServiceImpl::convertToDto).collect(Collectors.toList());
    }

    /**
     * 数据格式转换
     * @param eventAdmin 活动管理员数据库表字段
     * @return 活动管理员信息
     */
    private static EventAdminDto convertToDto(EventAdmin eventAdmin) {
        EventAdminDto eventAdminDto = new EventAdminDto();
        eventAdminDto.setEventId(eventAdmin.getEventId());
        eventAdminDto.setEventName(staticEventMapper.selectById(eventAdmin.getEventId()).getEventName());
        eventAdminDto.setUserId(eventAdmin.getUserId());
        eventAdminDto.setUserAccount(staticUserMapper.selectById(eventAdmin).getUserAccount());
        return eventAdminDto;
    }
}




