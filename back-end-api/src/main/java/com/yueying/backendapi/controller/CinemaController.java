package com.yueying.backendapi.controller;

import com.yueying.backendapi.model.domain.request.AddCinemaRequest;
import com.yueying.backendapi.model.domain.request.DeleteCinemaRequest;
import com.yueying.backendapi.model.domain.request.SearchCinemaRequest;
import com.yueying.backendapi.model.domain.request.UpdateCinemaInfoRequest;
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

    @PutMapping("/update")
    public ResponseEntity<Object> updateCinemaInfo(@RequestBody UpdateCinemaInfoRequest updateCinemaInfoRequest, HttpServletRequest httpServletRequest) {
        return cinemaService.updateCinemaInfo(updateCinemaInfoRequest, httpServletRequest);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteCinema(@RequestParam Long cinemaId, HttpServletRequest httpServletRequest) {
        DeleteCinemaRequest deleteCinemaRequest = new DeleteCinemaRequest();
        deleteCinemaRequest.setCinemaId(cinemaId);
        return cinemaService.deleteCinema(deleteCinemaRequest, httpServletRequest);
    }

    // TODO: 搜索影院上映影片需要影片场次mapper，在影片场次完成后完成该功能
}
