package com.yueying.backendapi.controller;

import com.yueying.backendapi.model.domain.request.AddCinemaRequest;
import com.yueying.backendapi.model.domain.request.SearchCinemaRequest;
import com.yueying.backendapi.service.CinemaService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cinema")
public class CinemaController {

    @Resource
    private CinemaService cinemaService;

    @GetMapping("/search")
    public ResponseEntity<Object> searchCinema(@RequestParam String cinemaName) {
        SearchCinemaRequest searchCinemaRequest = new SearchCinemaRequest();
        searchCinemaRequest.setCinemaName(cinemaName);
        return cinemaService.searchCinema(searchCinemaRequest);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addCinema(@RequestBody AddCinemaRequest addCinemaRequest, HttpServletRequest httpServletRequest) {
        return cinemaService.addCinema(addCinemaRequest, httpServletRequest);
    }
}
