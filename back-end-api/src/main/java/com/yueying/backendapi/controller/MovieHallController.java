package com.yueying.backendapi.controller;

import com.yueying.backendapi.model.domain.request.*;
import com.yueying.backendapi.service.MovieHallService;
import com.yueying.backendapi.service.MovieHallTypeService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movie-hall")
public class MovieHallController {

    @Resource
    private MovieHallTypeService movieHallTypeService;

    @Resource
    private MovieHallService movieHallService;

    @GetMapping("/search")
    public ResponseEntity<Object> searchMovieHall(@RequestParam Long cinemaId) {
        SearchMovieHallRequest searchMovieHallRequest = new SearchMovieHallRequest();
        searchMovieHallRequest.setCinemaId(cinemaId);
        return movieHallService.searchMovieHall(searchMovieHallRequest);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addMovieHall(@RequestBody AddMovieHallRequest addMovieHallRequest, HttpServletRequest httpServletRequest) {
        return movieHallService.addMovieHall(addMovieHallRequest, httpServletRequest);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateMovieHall(@RequestBody UpdateMovieHallRequest updateMovieHallRequest, HttpServletRequest httpServletRequest) {
        return movieHallService.updateMovieHall(updateMovieHallRequest, httpServletRequest);
    }

    @GetMapping("/search-type")
    public ResponseEntity<Object> searchMovieHallType(@RequestParam Long movieHallId) {
        SearchMovieHallTypeRequest searchMovieHallTypeRequest = new SearchMovieHallTypeRequest();
        searchMovieHallTypeRequest.setTypeId(movieHallId);
        return movieHallTypeService.searchMovieHallType(searchMovieHallTypeRequest);
    }

    @PostMapping("/add-type")
    public ResponseEntity<Object> addMovieHallType(@RequestBody AddMovieHallTypeRequest addMovieHallTypeRequest, HttpServletRequest httpServletRequest) {
        return movieHallTypeService.addMovieHallType(addMovieHallTypeRequest, httpServletRequest);
    }

    @PutMapping("/update-type")
    public ResponseEntity<Object> updateMovieHallType(@RequestBody UpdateMovieHallTypeRequest updateMovieHallTypeRequest, HttpServletRequest httpServletRequest) {
        return movieHallTypeService.updateMovieHallType(updateMovieHallTypeRequest, httpServletRequest);
    }

    @DeleteMapping("/delete-type")
    public ResponseEntity<Object> deleteMovieHallType(@RequestParam Long movieHallTypeId, HttpServletRequest httpServletRequest) {
        DeleteMovieHallTypeRequest deleteMovieHallTypeRequest = new DeleteMovieHallTypeRequest();
        deleteMovieHallTypeRequest.setMovieHallTypeId(movieHallTypeId);
        return movieHallTypeService.deleteMovieHallType(deleteMovieHallTypeRequest, httpServletRequest);
    }
}
