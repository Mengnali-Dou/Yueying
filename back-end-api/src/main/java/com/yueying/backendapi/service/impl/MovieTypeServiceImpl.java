package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.model.domain.MovieType;
import com.yueying.backendapi.model.domain.request.SearchMovieTypeRequest;
import com.yueying.backendapi.model.domain.response.MovieTypeDto;
import com.yueying.backendapi.service.MovieTypeService;
import com.yueying.backendapi.mapper.MovieTypeMapper;
import com.yueying.backendapi.utils.ResponseData;
import jakarta.annotation.Resource;
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




