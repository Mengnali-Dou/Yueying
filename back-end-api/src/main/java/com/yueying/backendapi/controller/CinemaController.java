package com.yueying.backendapi.controller;

import com.yueying.backendapi.model.domain.request.SearchCinemaRequest;
import com.yueying.backendapi.service.CinemaService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
