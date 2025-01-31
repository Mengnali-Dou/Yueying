package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.model.domain.User;
import com.yueying.backendapi.model.domain.request.UserLoginRequest;
import com.yueying.backendapi.service.UserService;
import com.yueying.backendapi.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
* @author @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_user(用户表)】的数据库操作Service实现
* @createDate 2025-01-31 16:49:10
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Override
    public ResponseEntity<Object> userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request) {
        return null;
    }
}




