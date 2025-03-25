package com.yueying.backendapi.controller;

import com.yueying.backendapi.model.domain.request.AddEventRequest;
import com.yueying.backendapi.model.domain.request.SearchEventRequest;
import com.yueying.backendapi.model.domain.request.UpdateEventRequest;
import com.yueying.backendapi.service.EventService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public ResponseEntity<Object> addEvent(@RequestBody AddEventRequest addEventRequest, HttpServletRequest httpServletRequest) {
        return eventService.addEvent(addEventRequest, httpServletRequest);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateEvent(@RequestBody UpdateEventRequest updateEventRequest, HttpServletRequest httpServletRequest) {
        return eventService.updateEvent(updateEventRequest, httpServletRequest);
    }
}
