package com.yueying.backendapi.controller;

import com.yueying.backendapi.model.domain.request.AddEventPlaceRequest;
import com.yueying.backendapi.model.domain.request.SearchEventPlaceRequest;
import com.yueying.backendapi.service.EventPlaceService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/event-place")
public class EventPlaceController {

    @Resource
    private EventPlaceService eventPlaceService;

    @GetMapping("/search")
    public ResponseEntity<Object> searchEventPlace(@RequestParam String placeName, @RequestParam String placeType) {
        SearchEventPlaceRequest searchEventPlaceRequest = new SearchEventPlaceRequest();
        searchEventPlaceRequest.setPlaceName(placeName);
        searchEventPlaceRequest.setPlaceType(placeType);
        return eventPlaceService.searchEventPlace(searchEventPlaceRequest);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addEventPlace(@RequestBody AddEventPlaceRequest addEventPlaceRequest, HttpServletRequest httpServletRequest) {
        return eventPlaceService.addEventPlace(addEventPlaceRequest, httpServletRequest);
    }
}
