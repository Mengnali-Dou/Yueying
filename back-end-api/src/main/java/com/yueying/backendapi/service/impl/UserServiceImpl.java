package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.model.domain.User;
import com.yueying.backendapi.model.domain.request.UserLoginRequest;
import com.yueying.backendapi.model.domain.request.UserRegisterRequest;
import com.yueying.backendapi.model.domain.response.ErrorResponseDto;
import com.yueying.backendapi.model.domain.response.SuccessResponseDto;
import com.yueying.backendapi.model.domain.response.UserInfoDto;
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

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 非空校验
        if (StringUtils.isAllBlank(userLoginRequest.getUserAccount(), userLoginRequest.getPassword())) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, PARAMETER_CANNOT_BE_NULL, errorResponseDto));
        }

        // 账号格式校验
        if (userLoginRequest.getUserAccount().length() < ACCOUNT_LENGTH_MIN) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, LOW_ACCOUNT_LENGTH, errorResponseDto));
        }
        if (!UserPublicClass.accountRegx(userLoginRequest.getUserAccount())) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, ACCOUNT_CANNOT_CONTAIN_SPECIAL_CHARACTERS, errorResponseDto));
        }

        // 密码格式校验
        if (UserPublicClass.cryptographicStrengthCheck(userLoginRequest.getPassword())) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, LOW_PASSWORD_STRENGTH, errorResponseDto));
        }

        // 验证用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userLoginRequest.getUserAccount());
        long num = userMapper.selectCount(queryWrapper);
        if (num == 0) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, USER_DOES_NOT_EXISTS, errorResponseDto));
        }

        // 加密
        String digestPassword = UserPublicClass.digestPassword(userLoginRequest.getPassword(), userLoginRequest.getUserAccount());

        // 验证密码
        queryWrapper.eq("user_password", digestPassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, PASSWORD_ERROR, errorResponseDto));
        }

        // 记录用户登录状态
        request.getSession().setAttribute(USER_LOGIN_STATE, user);

        return ResponseEntity.ok(ResponseData.responseData(OK, LOGIN_SUCCESSFULLY, convertToDto(user)));
    }

    /**
     * 数据格式转换
     * @param user 数据库表字段
     * @return 用户信息dto
     */
    private static UserInfoDto convertToDto(User user) {
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setUserId(user.getUserId());
        userInfoDto.setUserName(user.getUserName());
        userInfoDto.setUserAccount(user.getUserAccount());
        userInfoDto.setAvatarUrl(user.getAvatarUrl());
        userInfoDto.setGender(user.getGender());
        userInfoDto.setPhone(user.getPhone());
        userInfoDto.setEmail(user.getEmail());
        userInfoDto.setCreateTime(user.getCreateTime());
        userInfoDto.setUserRole(user.getUserRole() == null ? 0 : user.getUserRole());
        return userInfoDto;
    }
}




