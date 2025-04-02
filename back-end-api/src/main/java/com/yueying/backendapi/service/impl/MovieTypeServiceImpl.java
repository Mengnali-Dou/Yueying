package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.model.domain.MovieType;
import com.yueying.backendapi.model.domain.request.AddMovieTypeRequest;
import com.yueying.backendapi.model.domain.request.SearchMovieTypeRequest;
import com.yueying.backendapi.model.domain.response.ErrorResponseDto;
import com.yueying.backendapi.model.domain.response.MovieTypeDto;
import com.yueying.backendapi.model.domain.response.SuccessResponseDto;
import com.yueying.backendapi.service.MovieTypeService;
import com.yueying.backendapi.mapper.MovieTypeMapper;
import com.yueying.backendapi.utils.ResponseData;
import com.yueying.backendapi.utils.UserPublicClass;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.yueying.backendapi.constant.ResponseStatus.*;
import static com.yueying.backendapi.constant.UniversalConstant.*;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_movie_type(影片类型)】的数据库操作Service实现
* @createDate 2025-04-02 21:43:58
*/
@Service
public class MovieTypeServiceImpl extends ServiceImpl<MovieTypeMapper, MovieType>
    implements MovieTypeService{

    @Resource
    private MovieTypeMapper movieTypeMapper;

    @Override
    public ResponseEntity<Object> searchMovieType(SearchMovieTypeRequest searchMovieTypeRequest) {

        QueryWrapper<MovieType> movieTypeQueryWrapper = new QueryWrapper<>();

        if (searchMovieTypeRequest.getMovieTypeId() > 0) {
            movieTypeQueryWrapper.eq("movie_type_id", searchMovieTypeRequest.getMovieTypeId());
        }

        return ResponseEntity.ok(ResponseData.responseData(OK, SEARCH_SUCCESSFULLY, convertToDtoList(movieTypeMapper.selectList(movieTypeQueryWrapper))));
    }

    @Override
    public ResponseEntity<Object> addMovieType(AddMovieTypeRequest addMovieTypeRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 必要参数是否为空
        if (StringUtils.isBlank(addMovieTypeRequest.getMovieTypeName())) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, PARAMETER_CANNOT_BE_NULL, errorResponseDto));
        }

        // 验证权限
        if (UserPublicClass.isAdmin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        // 添加
        MovieType movieType = new MovieType();
        movieType.setMovieType(addMovieTypeRequest.getMovieTypeName());
        boolean addMovieType = this.save(movieType);
        if (!addMovieType) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ResponseData.responseData(INTERNAL_SERVER_ERROR, FAILED_TO_INSERT, errorResponseDto));
        }

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, INSERT_SUCCESSFULLY, successResponseDto));
    }

    /**
     * 数据格式转换
     * @param movieTypeList 影片类型数据库表字段列表
     * @return 影片类型列表
     */
    private List<MovieTypeDto> convertToDtoList(List<MovieType> movieTypeList) {
        return movieTypeList.stream().map(MovieTypeServiceImpl::convertToDto).collect(Collectors.toList());
    }

    /**
     * 数据格式转换
     * @param movieType 影片类型数据库表字段
     * @return 影片类型
     */
    private static MovieTypeDto convertToDto(MovieType movieType) {
        MovieTypeDto movieTypeDto = new MovieTypeDto();
        movieTypeDto.setMovieTypeId(movieType.getMovieTypeId());
        movieTypeDto.setMovieType(movieType.getMovieType());
        return movieTypeDto;
    }
}




