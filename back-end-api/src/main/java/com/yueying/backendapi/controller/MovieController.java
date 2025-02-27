package com.yueying.backendapi.controller;

import com.yueying.backendapi.model.domain.request.SearchMovieRequest;
import com.yueying.backendapi.service.MovieService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Resource
    private MovieService movieService;

    @GetMapping("/search")
    public ResponseEntity<Object> SearchMovieInfo(@RequestParam String movieName, @RequestParam String movieType) {

        SearchMovieRequest searchMovieRequest = new SearchMovieRequest();
        searchMovieRequest.setMovieName(movieName);
        searchMovieRequest.setMovieType(movieType);

        return movieService.searchMovieInfo(searchMovieRequest);
    }
}
