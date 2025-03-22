package com.yueying.backendapi.controller;

import com.yueying.backendapi.model.domain.request.*;
import com.yueying.backendapi.service.MovieService;
import com.yueying.backendapi.service.MovieSessionService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Resource
    private MovieService movieService;

    @Resource
    private MovieSessionService movieSessionService;

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

    @GetMapping("/search-session")
    private ResponseEntity<Object> searchMovieSession(@RequestParam Long sessionId, @RequestParam Long movieId, @RequestParam Long cinemaId, @RequestParam String movieRunDate) {
        SearchMovieSessionRequest searchMovieSessionRequest = new SearchMovieSessionRequest();
        searchMovieSessionRequest.setSessionId(sessionId);
        searchMovieSessionRequest.setMovieId(movieId);
        searchMovieSessionRequest.setCinemaId(cinemaId);
        searchMovieSessionRequest.setMovieRunDate(movieRunDate);
        return movieSessionService.searchMovieSession(searchMovieSessionRequest);
    }
}
