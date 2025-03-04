package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.mapper.CinemaMapper;
import com.yueying.backendapi.mapper.MovieHallTypeMapper;
import com.yueying.backendapi.model.domain.MovieHall;
import com.yueying.backendapi.model.domain.request.SearchMovieHallRequest;
import com.yueying.backendapi.model.domain.response.MovieHallInfoDto;
import com.yueying.backendapi.service.MovieHallService;
import com.yueying.backendapi.mapper.MovieHallMapper;
import com.yueying.backendapi.utils.ResponseData;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.yueying.backendapi.constant.ResponseStatus.*;
import static com.yueying.backendapi.constant.UniversalConstant.*;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_movie_hall(影厅)】的数据库操作Service实现
* @createDate 2025-03-04 16:20:09
*/
@Service
public class MovieHallServiceImpl extends ServiceImpl<MovieHallMapper, MovieHall>
    implements MovieHallService{

    @Resource
    private MovieHallMapper movieHallMapper;

    @Resource
    private MovieHallTypeMapper movieHallTypeMapper;

    @Resource
    private CinemaMapper cinemaMapper;

    private static CinemaMapper staticCinemaMapper;
    private static MovieHallTypeMapper staticMovieHallTypeMapper;

    @PostConstruct
    public void init() {
        staticCinemaMapper = cinemaMapper;
        staticMovieHallTypeMapper = movieHallTypeMapper;
    }

    @Override
    public ResponseEntity<Object> searchMovieHall(SearchMovieHallRequest searchMovieHallRequest) {

        QueryWrapper<MovieHall> movieHallQueryWrapper = new QueryWrapper<>();
        if (searchMovieHallRequest.getCinemaId() > 0) {
            movieHallQueryWrapper.eq("cinema_id", searchMovieHallRequest);
        }

        return ResponseEntity.ok(ResponseData.responseData(OK, SEARCH_SUCCESSFULLY, convertToDtoList(movieHallMapper.selectList(movieHallQueryWrapper))));
    }

    /**
     * 数据格式转换
     * @param movieHalls 影厅数据库表字段列表
     * @return 影厅信息列表
     */
    private List<MovieHallInfoDto> convertToDtoList(List<MovieHall> movieHalls) {
        return movieHalls.stream().map(MovieHallServiceImpl::convertToDto).collect(Collectors.toList());
    }

    /**
     * 数据格式转换
     * @param movieHall 影厅数据库表字段
     * @return 影厅信息
     */
    private static MovieHallInfoDto convertToDto(MovieHall movieHall) {
        MovieHallInfoDto movieHallInfoDto = new MovieHallInfoDto();
        movieHallInfoDto.setMovieHallId(movieHall.getMovieHallId());
        movieHallInfoDto.setCinemaId(movieHall.getCinemaId());
        movieHallInfoDto.setCinemaName(staticCinemaMapper.selectById(movieHall.getCinemaId()).getCinemaName());
        movieHallInfoDto.setMovieHallName(movieHall.getMovieHallName());
        movieHallInfoDto.setMovieHallTypeId(movieHall.getMovieHallTypeId());
        movieHallInfoDto.setMovieHallTypeName(staticMovieHallTypeMapper.selectById(movieHall.getMovieHallTypeId()).getTypeName());
        movieHallInfoDto.setMovieHallPhoto(movieHall.getMovieHallPhoto());
        movieHallInfoDto.setMovieHallProfile(movieHall.getMovieHallProfile());
        movieHallInfoDto.setSeating(movieHall.getSeating());
        return movieHallInfoDto;
    }
}




