package com.yueying.backendapi.service;

import com.yueying.backendapi.model.domain.EventAdmin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yueying.backendapi.model.domain.request.AddEventAdminRequest;
import com.yueying.backendapi.model.domain.request.SearchEventAdminRequest;
import com.yueying.backendapi.model.domain.request.UpdateEventAdminRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_event_admin(活动管理员)】的数据库操作Service
* @createDate 2025-03-23 18:43:19
*/
public interface EventAdminService extends IService<EventAdmin> {

    /**
     * 搜索活动管理员
     * @param searchEventAdminRequest 搜索活动管理员请求体
     * @param httpServletRequest http请求信息
     * @return 活动管理员列表
     */
    ResponseEntity<Object> searchEventAdmin(SearchEventAdminRequest searchEventAdminRequest, HttpServletRequest httpServletRequest);

    /**
     * 添加活动管理员
     * @param addEventAdminRequest 添加活动管理员请求体
     * @param httpServletRequest http请求信息
     * @return 是否添加成功
     */
    ResponseEntity<Object> addEventAdmin(AddEventAdminRequest addEventAdminRequest, HttpServletRequest httpServletRequest);

    /**
     * 修改活动管理员信息
     * @param updateEventAdminRequest 修改活动管理员信息请求体
     * @param httpServletRequest http请求信息
     * @return 是否修改成功
     */
    ResponseEntity<Object> updateEventAdmin(UpdateEventAdminRequest updateEventAdminRequest, HttpServletRequest httpServletRequest);
}
