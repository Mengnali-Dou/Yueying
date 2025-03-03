package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.model.domain.MovieHallType;
import com.yueying.backendapi.model.domain.request.AddMovieHallTypeRequest;
import com.yueying.backendapi.model.domain.request.SearchMovieHallTypeRequest;
import com.yueying.backendapi.model.domain.response.ErrorResponseDto;
import com.yueying.backendapi.model.domain.response.MovieHallTypeDto;
import com.yueying.backendapi.model.domain.response.SuccessResponseDto;
import com.yueying.backendapi.service.MovieHallTypeService;
import com.yueying.backendapi.mapper.MovieHallTypeMapper;
import com.yueying.backendapi.utils.ResponseData;
import com.yueying.backendapi.utils.UserPublicClass;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.yueying.backendapi.constant.MovieHallMessage.*;
import static com.yueying.backendapi.constant.ResponseStatus.*;
import static com.yueying.backendapi.constant.UniversalConstant.*;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_movie_hall_type(影厅类型)】的数据库操作Service实现
* @createDate 2025-03-01 19:22:45
*/
@Service
public class MovieHallTypeServiceImpl extends ServiceImpl<MovieHallTypeMapper, MovieHallType>
    implements MovieHallTypeService{

    @Resource
    private MovieHallTypeMapper movieHallTypeMapper;

    @Override
    public ResponseEntity<Object> searchMovieHallType(SearchMovieHallTypeRequest searchMovieHallTypeRequest) {

        QueryWrapper<MovieHallType>  movieHallTypeQueryWrapper = new QueryWrapper<>();
        if (searchMovieHallTypeRequest.getTypeId() != 0) {
            movieHallTypeQueryWrapper.eq("type_id", searchMovieHallTypeRequest.getTypeId());
        }

        return ResponseEntity.ok(ResponseData.responseData(OK, SEARCH_SUCCESSFULLY, convertToDtoList(movieHallTypeMapper.selectList(movieHallTypeQueryWrapper))));
    }

    @Override
    public ResponseEntity<Object> addMovieHallType(AddMovieHallTypeRequest addMovieHallTypeRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 必要参数是否为空
        if (StringUtils.isBlank(addMovieHallTypeRequest.getMovieHallTypeName())) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, PARAMETER_CANNOT_BE_NULL, errorResponseDto));
        }

        // 验证权限
        if (UserPublicClass.isAdmin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        // 是否存在
        QueryWrapper<MovieHallType> movieHallTypeQueryWrapper = new QueryWrapper<>();
        movieHallTypeQueryWrapper.eq("hall_name", addMovieHallTypeRequest.getMovieHallTypeName());
        if (movieHallTypeMapper.selectCount(movieHallTypeQueryWrapper) > 0) {
            return ResponseEntity.status(CONFLICT).body(ResponseData.responseData(CONFLICT, MOVIE_HALL_TYPE_ALREADY_EXISTS, errorResponseDto));
        }

        // 添加
        MovieHallType movieHallType = new MovieHallType();
        movieHallType.setTypeName(addMovieHallTypeRequest.getMovieHallTypeName());

        boolean addMovieHallType = this.save(movieHallType);
        if (!addMovieHallType) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ResponseData.responseData(INTERNAL_SERVER_ERROR, FAILED_TO_INSERT, errorResponseDto));
        }

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, INSERT_SUCCESSFULLY, successResponseDto));
    }

    /**
     * 数据格式转换
     * @param movieHallTypeList 影厅类型数据库表字段列表
     * @return 影厅类型信息列表
     */
    private List<MovieHallTypeDto> convertToDtoList(List<MovieHallType> movieHallTypeList) {
        return movieHallTypeList.stream().map(MovieHallTypeServiceImpl::convertToDto).collect(Collectors.toList());
    }

    /**
     * 数据格式转换
     * @param movieHallType 影厅类型数据库表字段
     * @return 影厅类型信息
     */
    static MovieHallTypeDto convertToDto(MovieHallType movieHallType) {
        MovieHallTypeDto movieHallTypeDto = new MovieHallTypeDto();
        movieHallTypeDto.setHallTypeId(movieHallType.getTypeId());
        movieHallTypeDto.setHallTypeName(movieHallType.getTypeName());
        return movieHallTypeDto;
    }
}




