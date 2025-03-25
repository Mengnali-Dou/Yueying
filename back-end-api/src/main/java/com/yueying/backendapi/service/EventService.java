package com.yueying.backendapi.service;

import com.yueying.backendapi.model.domain.Event;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yueying.backendapi.model.domain.request.AddEventRequest;
import com.yueying.backendapi.model.domain.request.DeleteEventRequest;
import com.yueying.backendapi.model.domain.request.SearchEventRequest;
import com.yueying.backendapi.model.domain.request.UpdateEventRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_event(活动)】的数据库操作Service
* @createDate 2025-03-25 10:48:32
*/
public interface EventService extends IService<Event> {

    /**
     * 搜索活动
     * @param searchEventRequest 搜索活动请求体
     * @return 活动信息列表
     */
    ResponseEntity<Object> searchEvent(SearchEventRequest searchEventRequest);

    /**
     * 添加活动
     * @param addEventRequest 添加活动请求体
     * @param httpServletRequest http请求信息
     * @return 是否添加成功
     */
    ResponseEntity<Object> addEvent(AddEventRequest addEventRequest, HttpServletRequest httpServletRequest);

    /**
     * 修改活动信息
     * @param updateEventRequest 修改活动信息请求体
     * @param httpServletRequest http请求信息
     * @return 是否修改成功
     */
    ResponseEntity<Object> updateEvent(UpdateEventRequest updateEventRequest, HttpServletRequest httpServletRequest);

    /**
     * 删除活动
     * @param deleteEventRequest 删除活动请求体
     * @param httpServletRequest http请求信息
     * @return 是否删除成功
     */
    ResponseEntity<Object> deleteEvent(DeleteEventRequest deleteEventRequest, HttpServletRequest httpServletRequest);
}
