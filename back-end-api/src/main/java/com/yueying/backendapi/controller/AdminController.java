package com.yueying.backendapi.controller;

import com.yueying.backendapi.model.domain.request.*;
import com.yueying.backendapi.service.CinemaAdminService;
import com.yueying.backendapi.service.EventAdminService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Resource
    private CinemaAdminService cinemaAdminService;

    @Resource
    private EventAdminService eventAdminService;

    @GetMapping("/cinema-search")
    public ResponseEntity<Object> searchCinemaAdmin(@RequestParam Long cinemaId, @RequestParam Long userId, HttpServletRequest httpServletRequest) {
        SearchCinemaAdminRequest searchCinemaAdminRequest = new SearchCinemaAdminRequest();
        searchCinemaAdminRequest.setCinemaId(cinemaId);
        searchCinemaAdminRequest.setUserId(userId);
        return cinemaAdminService.searchCinemaAdmin(searchCinemaAdminRequest, httpServletRequest);
    }

    @PostMapping("/cinema-add")
    public ResponseEntity<Object> addCinemaAdmin(@RequestBody AddCinemaAdminRequest addCinemaAdminRequest, HttpServletRequest httpServletRequest) {
        return cinemaAdminService.addCinemaAdmin(addCinemaAdminRequest, httpServletRequest);
    }

    @PutMapping("/cinema-update")
    public ResponseEntity<Object> updateCinemaAdmin(@RequestBody UpdateCinemaAdminRequest updateCinemaAdminRequest, HttpServletRequest httpServletRequest) {
        return cinemaAdminService.updateCinemaAdmin(updateCinemaAdminRequest, httpServletRequest);
    }

    @DeleteMapping("/cinema-delete")
    public ResponseEntity<Object> deleteCinemaAdmin(@RequestParam Long cinemaAdminId, HttpServletRequest httpServletRequest) {
        DeleteCinemaAdminRequest deleteCinemaAdminRequest = new DeleteCinemaAdminRequest();
        deleteCinemaAdminRequest.setCinemaAdminId(cinemaAdminId);
        return cinemaAdminService.deleteCinemaAdmin(deleteCinemaAdminRequest, httpServletRequest);
    }

    @GetMapping("/event-search")
    public ResponseEntity<Object> searchEventAdmin(@RequestParam Long eventId, @RequestParam Long userId, HttpServletRequest httpServletRequest) {
        SearchEventAdminRequest searchEventAdminRequest = new SearchEventAdminRequest();
        searchEventAdminRequest.setEventId(eventId);
        searchEventAdminRequest.setUserId(userId);
        return eventAdminService.searchEventAdmin(searchEventAdminRequest, httpServletRequest);

    }

    @PostMapping("/event-add")
    public ResponseEntity<Object> addEventAdmin(@RequestBody AddEventAdminRequest addEventAdminRequest, HttpServletRequest httpServletRequest) {
        return eventAdminService.addEventAdmin(addEventAdminRequest, httpServletRequest);
    }
}
