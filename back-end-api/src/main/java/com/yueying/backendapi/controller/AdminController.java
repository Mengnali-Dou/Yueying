package com.yueying.backendapi.controller;

import com.yueying.backendapi.model.domain.request.SearchCinemaAdminRequest;
import com.yueying.backendapi.model.domain.request.UpdateCinemaAdminRequest;
import com.yueying.backendapi.service.CinemaAdminService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Resource
    private CinemaAdminService cinemaAdminService;

    @GetMapping("/cinema-search")
    public ResponseEntity<Object> searchCinemaAdmin(@RequestParam Long cinemaId, @RequestParam Long userId, HttpServletRequest httpServletRequest) {
        SearchCinemaAdminRequest searchCinemaAdminRequest = new SearchCinemaAdminRequest();
        searchCinemaAdminRequest.setCinemaId(cinemaId);
        searchCinemaAdminRequest.setUserId(userId);
        return cinemaAdminService.searchCinemaAdmin(searchCinemaAdminRequest, httpServletRequest);
    }

    @PutMapping("/cinema-update")
    public ResponseEntity<Object> updateCinemaAdmin(@RequestBody UpdateCinemaAdminRequest updateCinemaAdminRequest, HttpServletRequest httpServletRequest) {
        return cinemaAdminService.updateCinemaAdmin(updateCinemaAdminRequest, httpServletRequest);
    }
}
