package com.yueying.backendapi.controller;

import com.yueying.backendapi.model.domain.request.SearchMovieHallTypeRequest;
import com.yueying.backendapi.service.MovieHallTypeService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movie-hall")
public class MovieHallController {

    @Resource
    private MovieHallTypeService movieHallTypeService;

    @GetMapping("/search-type")
    public ResponseEntity<Object> searchMovieHallType(@RequestParam Long movieHallId) {
        SearchMovieHallTypeRequest searchMovieHallTypeRequest = new SearchMovieHallTypeRequest();
        searchMovieHallTypeRequest.setTypeId(movieHallId);
        return movieHallTypeService.searchMovieHallType(searchMovieHallTypeRequest);
    }
}
