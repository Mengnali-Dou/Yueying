package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.mapper.MovieMapper;
import com.yueying.backendapi.mapper.MovieTypeMapper;
import com.yueying.backendapi.model.domain.Movie;
import com.yueying.backendapi.model.domain.MovieType;
import com.yueying.backendapi.model.domain.request.AddMovieRequest;
import com.yueying.backendapi.model.domain.request.DeleteMovieRequest;
import com.yueying.backendapi.model.domain.request.SearchMovieRequest;
import com.yueying.backendapi.model.domain.request.UpdateMovieRequest;
import com.yueying.backendapi.model.domain.response.ErrorResponseDto;
import com.yueying.backendapi.model.domain.response.MovieInfoDto;
import com.yueying.backendapi.model.domain.response.SuccessResponseDto;
import com.yueying.backendapi.service.MovieService;
import com.yueying.backendapi.utils.PublicMethods;
import com.yueying.backendapi.utils.ResponseData;
import com.yueying.backendapi.utils.UserPublicClass;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.yueying.backendapi.constant.MovieMessage.*;
import static com.yueying.backendapi.constant.ResponseStatus.*;
import static com.yueying.backendapi.constant.UniversalConstant.*;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_movie(影片)】的数据库操作Service实现
* @createDate 2025-02-27 14:27:42
*/
@Service
public class MovieServiceImpl extends ServiceImpl<MovieMapper, Movie>
    implements MovieService {

    @Resource
    private MovieMapper movieMapper;

    @Resource
    private MovieTypeMapper movieTypeMapper;

    private static MovieTypeMapper staticMovieTypeMapper;

    @PostConstruct
    private void init() {
        staticMovieTypeMapper = movieTypeMapper;
    }

    @Override
    public ResponseEntity<Object> searchMovieInfo(SearchMovieRequest searchMovieRequest) {

        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(searchMovieRequest.getMovieName())) {
            queryWrapper.like("movie_name", searchMovieRequest.getMovieName());
        }
        if (StringUtils.isNotBlank(searchMovieRequest.getMovieType())) {
            queryWrapper.like("movie_type", searchMovieRequest.getMovieType());
        }

        return ResponseEntity.ok(ResponseData.responseData(OK, SEARCH_SUCCESSFULLY, convertToDtoList(movieMapper.selectList(queryWrapper))));
    }

    @Override
    public ResponseEntity<Object> addMovie(AddMovieRequest addMovieRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 参数是否为空
        if (!StringUtils.isNoneBlank(addMovieRequest.getMovieName(), addMovieRequest.getReleaseDate(), addMovieRequest.getMovieDuration()) || addMovieRequest.getMovieTypeId() < 0) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, PARAMETER_CANNOT_BE_NULL, errorResponseDto));
        }

        // 验证权限
        if (UserPublicClass.isAdmin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        // 影片类型是否存在
        QueryWrapper<MovieType> movieTypeQueryWrapper = new QueryWrapper<>();
        movieTypeQueryWrapper.eq("movie_type_id", addMovieRequest.getMovieTypeId());
        if (movieTypeMapper.selectCount(movieTypeQueryWrapper) <= 0) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, MOVIE_TYPE_NONENTITY, errorResponseDto));
        }

        // 添加
        Movie movie = new Movie();
        movie.setMovieName(addMovieRequest.getMovieName());

        movie.setMovieTypeId(addMovieRequest.getMovieTypeId());

        if (StringUtils.isNotBlank(addMovieRequest.getMovieCoverLarge())) {
            movie.setMovieCoverLarge(addMovieRequest.getMovieCoverLarge());
        }
        if (StringUtils.isNotBlank(addMovieRequest.getMovieCoverSmall())) {
            movie.setMovieCoverSmall(addMovieRequest.getMovieCoverSmall());
        }
        movie.setReleaseDate(PublicMethods.stringConvertToDateTime(addMovieRequest.getReleaseDate()));
        movie.setMovieDuration(PublicMethods.stringConvertToTime(addMovieRequest.getMovieDuration()));
        if (StringUtils.isNotBlank(addMovieRequest.getMainActor())) {
            movie.setMainActor(addMovieRequest.getMainActor());
        }
        if (StringUtils.isNotBlank(addMovieRequest.getMovieProfile())) {
            movie.setMovieProfile(addMovieRequest.getMovieProfile());
        }
        boolean addMovie = this.save(movie);
        if (!addMovie) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ResponseData.responseData(INTERNAL_SERVER_ERROR, FAILED_TO_INSERT, errorResponseDto));
        }

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, INSERT_SUCCESSFULLY, successResponseDto));
    }

    @Override
    public ResponseEntity<Object> updateMovieInfo(UpdateMovieRequest updateMovieRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 验证权限
        if (UserPublicClass.isAdmin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        // 是否存在
        QueryWrapper<Movie> movieQueryWrapper = new QueryWrapper<>();
        movieQueryWrapper.eq("movie_id", updateMovieRequest.getMovieId());
        if (movieMapper.selectCount(movieQueryWrapper) <= 0) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, MOVIE_NONENTITY, errorResponseDto));
        }

        // 修改
        Movie movie = new Movie();
        movie.setMovieId(updateMovieRequest.getMovieId());
        if (StringUtils.isNotBlank(updateMovieRequest.getMovieName())) {
            movie.setMovieName(updateMovieRequest.getMovieName());
        }

        if (updateMovieRequest.getMovieTypeId() >= 0) {
            // 影片类型是否存在
            QueryWrapper<MovieType> movieTypeQueryWrapper = new QueryWrapper<>();
            movieTypeQueryWrapper.eq("movie_type_id", updateMovieRequest.getMovieTypeId());
            if (movieTypeMapper.selectCount(movieTypeQueryWrapper) <= 0) {
                return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, MOVIE_TYPE_NONENTITY, errorResponseDto));
            }
            movie.setMovieTypeId(updateMovieRequest.getMovieTypeId());
        }

        if (StringUtils.isNotBlank(updateMovieRequest.getMovieCoverLarge())) {
            movie.setMovieCoverLarge(updateMovieRequest.getMovieCoverLarge());
        }
        if (StringUtils.isNotBlank(updateMovieRequest.getMovieCoverSmall())) {
            movie.setMovieCoverSmall(updateMovieRequest.getMovieCoverSmall());
        }
        if (StringUtils.isNotBlank(updateMovieRequest.getReleaseDate())) {
            movie.setReleaseDate(PublicMethods.stringConvertToDateTime(updateMovieRequest.getReleaseDate()));
        }
        if (StringUtils.isNotBlank(updateMovieRequest.getMovieDuration())) {
            movie.setMovieDuration(PublicMethods.stringConvertToTime(updateMovieRequest.getMovieDuration()));
        }
        if (StringUtils.isNotBlank(updateMovieRequest.getMainActor())) {
            movie.setMainActor(updateMovieRequest.getMainActor());
        }
        if (StringUtils.isNotBlank(updateMovieRequest.getMovieProfile())) {
            movie.setMovieProfile(updateMovieRequest.getMovieProfile());
        }
        movieMapper.updateById(movie);

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, UPDATE_SUCCESSFULLY, successResponseDto));
    }

    @Override
    public ResponseEntity<Object> deleteMovie(DeleteMovieRequest deleteMovieRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 验证权限
        if (UserPublicClass.isAdmin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        // 是否存在
        QueryWrapper<Movie> movieQueryWrapper = new QueryWrapper<>();
        movieQueryWrapper.eq("movie_id", deleteMovieRequest.getMovieId());
        if (movieMapper.selectCount(movieQueryWrapper) <= 0) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, MOVIE_NONENTITY, errorResponseDto));
        }

        // 删除
        boolean deleted = this.removeById(deleteMovieRequest.getMovieId());
        if (!deleted) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ResponseData.responseData(INTERNAL_SERVER_ERROR, DELETE_FAILED, errorResponseDto));
        }

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, DELETE_SUCCESSFULLY, successResponseDto));
    }

    /**
     * 数据格式转换
     * @param movies 影片数据库表字段列表
     * @return 影片信息列表
     */
    private List<MovieInfoDto> convertToDtoList(List<Movie> movies) {
        return movies.stream().map(MovieServiceImpl::convertToDto).collect(Collectors.toList());
    }

    /**
     * 数据格式转换
     * @param movie 影片数据库表字段
     * @return 影片信息
     */
    private static MovieInfoDto convertToDto(Movie movie) {
        MovieInfoDto movieInfoDto = new MovieInfoDto();
        movieInfoDto.setMovieId(movie.getMovieId());
        movieInfoDto.setMovieName(movie.getMovieName());
        movieInfoDto.setMovieTypeId(movie.getMovieTypeId());
        movieInfoDto.setMovieName(staticMovieTypeMapper.selectById(movie.getMovieTypeId()).getMovieType());
        movieInfoDto.setMovieCoverLarge(movie.getMovieCoverLarge());
        movieInfoDto.setMovieCoverSmall(movie.getMovieCoverSmall());
        movieInfoDto.setReleaseDate(PublicMethods.dateTimeConvertToString(movie.getReleaseDate()));
        movieInfoDto.setMovieDuration(PublicMethods.timeConvertToString(movie.getMovieDuration()));
        movieInfoDto.setMainActor(movie.getMainActor());
        movieInfoDto.setMovieProfile(movie.getMovieProfile());
        return movieInfoDto;
    }
}




