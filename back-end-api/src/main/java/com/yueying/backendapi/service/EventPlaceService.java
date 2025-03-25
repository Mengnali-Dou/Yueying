package com.yueying.backendapi.service;

import com.yueying.backendapi.model.domain.EventPlace;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yueying.backendapi.model.domain.request.SearchEventPlaceRequest;
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
}
