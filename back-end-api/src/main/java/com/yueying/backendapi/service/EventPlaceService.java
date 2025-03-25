package com.yueying.backendapi.service;

import com.yueying.backendapi.model.domain.EventPlace;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yueying.backendapi.model.domain.request.AddEventPlaceRequest;
import com.yueying.backendapi.model.domain.request.SearchEventPlaceRequest;
import com.yueying.backendapi.model.domain.request.UpdateEventPlaceInfoRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_event_place(活动场地)】的数据库操作Service
* @createDate 2025-03-25 11:08:42
*/
public interface EventPlaceService extends IService<EventPlace> {

    /**
     * 搜索活动场地
     * @param searchEventPlaceRequest 搜索活动场地请求体
     * @return 活动场地列表
     */
    ResponseEntity<Object> searchEventPlace(SearchEventPlaceRequest searchEventPlaceRequest);

    /**
     * 添加活动场地
     * @param addEventPlaceRequest 添加活动场地请求体
     * @param httpServletRequest http请求信息
     * @return 是否添加成功
     */
    ResponseEntity<Object> addEventPlace(AddEventPlaceRequest addEventPlaceRequest, HttpServletRequest httpServletRequest);

    /**
     * 修改活动场地信息
     * @param updateEventPlaceInfoRequest 修改活动场地信息请求体
     * @param httpServletRequest http请求信息
     * @return 是否修改成功
     */
    ResponseEntity<Object> updateEventPlace(UpdateEventPlaceInfoRequest updateEventPlaceInfoRequest, HttpServletRequest httpServletRequest);
}
