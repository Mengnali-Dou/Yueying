package com.yueying.backendapi.controller;

import com.yueying.backendapi.model.domain.request.SearchEventPlaceRequest;
import com.yueying.backendapi.service.EventPlaceService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
