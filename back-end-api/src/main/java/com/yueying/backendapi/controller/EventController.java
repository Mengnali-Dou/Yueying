package com.yueying.backendapi.controller;

import com.yueying.backendapi.model.domain.request.SearchEventRequest;
import com.yueying.backendapi.service.EventService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Resource
    private EventService eventService;

    @GetMapping("/search")
    public ResponseEntity<Object> searchEvent(@RequestParam String eventName, @RequestParam Long eventPlaceId, @RequestParam String eventType) {
        SearchEventRequest searchEventRequest = new SearchEventRequest();
        searchEventRequest.setEventName(eventName);
        searchEventRequest.setEventPlaceId(eventPlaceId);
        searchEventRequest.setEventType(eventType);
        return eventService.searchEvent(searchEventRequest);
    }
}
