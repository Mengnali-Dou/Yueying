package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.model.domain.MovieType;
import com.yueying.backendapi.service.MovieTypeService;
import com.yueying.backendapi.mapper.MovieTypeMapper;
import org.springframework.stereotype.Service;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_movie_type(影片类型)】的数据库操作Service实现
* @createDate 2025-04-02 21:43:58
*/
@Service
public class MovieTypeServiceImpl extends ServiceImpl<MovieTypeMapper, MovieType>
    implements MovieTypeService{

}




