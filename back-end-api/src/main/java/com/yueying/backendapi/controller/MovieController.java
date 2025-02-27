package com.yueying.backendapi.controller;

import com.yueying.backendapi.model.domain.request.AddMovieRequest;
import com.yueying.backendapi.model.domain.request.DeleteMovieRequest;
import com.yueying.backendapi.model.domain.request.SearchMovieRequest;
import com.yueying.backendapi.model.domain.request.UpdateMovieRequest;
import com.yueying.backendapi.service.MovieService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping("/add")
    public ResponseEntity<Object> AddMovie(@RequestBody AddMovieRequest addMovieRequest, HttpServletRequest httpServletRequest) {
        return movieService.addMovie(addMovieRequest, httpServletRequest);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> UpdateMovieInfo(@RequestBody UpdateMovieRequest updateMovieRequest, HttpServletRequest httpServletRequest) {
        return movieService.updateMovieInfo(updateMovieRequest, httpServletRequest);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> DeleteMovie(@RequestParam Long movieId, HttpServletRequest httpServletRequest) {
        DeleteMovieRequest deleteMovieRequest = new DeleteMovieRequest();
        deleteMovieRequest.setMovieId(movieId);
        return movieService.deleteMovie(deleteMovieRequest, httpServletRequest);
    }
}
