package com.yueying.backendapi.controller;

import com.yueying.backendapi.model.domain.request.AddMovieHallTypeRequest;
import com.yueying.backendapi.model.domain.request.DeleteMovieHallTypeRequest;
import com.yueying.backendapi.model.domain.request.SearchMovieHallTypeRequest;
import com.yueying.backendapi.model.domain.request.UpdateMovieHallTypeRequest;
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
