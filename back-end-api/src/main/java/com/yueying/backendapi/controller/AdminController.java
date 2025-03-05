package com.yueying.backendapi.controller;

import com.yueying.backendapi.model.domain.request.SearchCinemaAdminRequest;
import com.yueying.backendapi.service.CinemaAdminService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
