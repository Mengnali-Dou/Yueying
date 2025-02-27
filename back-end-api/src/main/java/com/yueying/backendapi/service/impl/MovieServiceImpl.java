package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.mapper.MovieMapper;
import com.yueying.backendapi.model.domain.Movie;
import com.yueying.backendapi.model.domain.request.SearchMovieRequest;
import com.yueying.backendapi.model.domain.response.MovieInfoDto;
import com.yueying.backendapi.service.MovieService;
import com.yueying.backendapi.utils.PublicMethods;
import com.yueying.backendapi.utils.ResponseData;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        // TODO: 数据表需要tb_movie_type，完善数据表后在这里用id查询并返回
        movieInfoDto.setMovieType(movie.getMovieTypeId().toString());
        movieInfoDto.setMovieCoverLarge(movie.getMovieCoverLarge());
        movieInfoDto.setMovieCoverSmall(movie.getMovieCoverSmall());
        movieInfoDto.setReleaseDate(PublicMethods.dateTimeConvertToString(movie.getReleaseDate()));
        movieInfoDto.setMovieDuration(PublicMethods.timeConvertToString(movie.getMovieDuration()));
        movieInfoDto.setMainActor(movie.getMainActor());
        movieInfoDto.setMovieProfile(movie.getMovieProfile());
        return movieInfoDto;
    }
}




