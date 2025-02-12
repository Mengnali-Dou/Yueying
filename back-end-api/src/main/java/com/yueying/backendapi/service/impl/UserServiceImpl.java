package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.model.domain.User;
import com.yueying.backendapi.model.domain.request.UserLoginRequest;
import com.yueying.backendapi.model.domain.request.UserRegisterRequest;
import com.yueying.backendapi.model.domain.response.ErrorResponseDto;
import com.yueying.backendapi.model.domain.response.SuccessResponseDto;
import com.yueying.backendapi.service.UserService;
import com.yueying.backendapi.mapper.UserMapper;
import com.yueying.backendapi.utils.ResponseData;
import com.yueying.backendapi.utils.UserPublicClass;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.yueying.backendapi.constant.ParameterLength.*;
import static com.yueying.backendapi.constant.ResponseStatus.*;
import static com.yueying.backendapi.constant.UniversalConstant.*;
import static com.yueying.backendapi.constant.UserConstant.*;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_user(用户表)】的数据库操作Service实现
* @createDate 2025-01-31 16:49:10
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public ResponseEntity<Object> userRegister(UserRegisterRequest userRegisterRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 非空校验
        if (!StringUtils.isNoneBlank(userRegisterRequest.getUserAccount(), userRegisterRequest.getUserName(), userRegisterRequest.getPassword())) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, PARAMETER_CANNOT_BE_NULL, errorResponseDto));
        }

        // 账号格式校验
        if (userRegisterRequest.getUserAccount().length() < ACCOUNT_LENGTH_MIN) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, LOW_ACCOUNT_LENGTH, errorResponseDto));
        }
        if (!UserPublicClass.accountRegx(userRegisterRequest.getUserAccount())) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, ACCOUNT_CANNOT_CONTAIN_SPECIAL_CHARACTERS, errorResponseDto));
        }

        // 密码格式校验
        if (UserPublicClass.cryptographicStrengthCheck(userRegisterRequest.getPassword())) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, LOW_PASSWORD_STRENGTH, errorResponseDto));
        }

        // 账号不重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userRegisterRequest.getUserAccount());
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return ResponseEntity.status(CONFLICT).body(ResponseData.responseData(CONFLICT, ACCOUNT_HAS_BEEN_REGISTERED, errorResponseDto));
        }

        // 加密
        String digestPassword = UserPublicClass.digestPassword(userRegisterRequest.getPassword(), userRegisterRequest.getUserAccount());

        // 插入数据
        User user = new User();
        user.setUserAccount(userRegisterRequest.getUserAccount());
        user.setUserName(userRegisterRequest.getUserName());
        user.setUserPassword(digestPassword);
        user.setAvatarUrl(userRegisterRequest.getAvatar());
        user.setGender(userRegisterRequest.getGender());
        user.setPhone(userRegisterRequest.getPhone());
        user.setEmail(userRegisterRequest.getEmail());
        boolean saveUserInfo = this.save(user);
        if (!saveUserInfo) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ResponseData.responseData(INTERNAL_SERVER_ERROR, FAILED_TO_INSERT, errorResponseDto));
        }

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, REGISTER_SUCCESSFULLY, successResponseDto));
    }

    @Override
    public ResponseEntity<Object> userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request) {
        return null;
    }
}




