package com.yueying.backendapi.controller;

import com.yueying.backendapi.model.domain.request.*;
import com.yueying.backendapi.service.EventPriceService;
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

    @Resource
    private EventPriceService eventPriceService;

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

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteEvent(@RequestParam Long eventId, HttpServletRequest httpServletRequest) {
        DeleteEventRequest deleteEventRequest = new DeleteEventRequest();
        deleteEventRequest.setEventId(eventId);
        return eventService.deleteEvent(deleteEventRequest, httpServletRequest);
    }

    @GetMapping("/search-price")
    public ResponseEntity<Object> searchEventPrice(@RequestParam Long eventId) {
        SearchEventPriceRequest searchEventPriceRequest = new SearchEventPriceRequest();
        searchEventPriceRequest.setEventId(eventId);
        return eventPriceService.searchEventPrice(searchEventPriceRequest);
    }

    @PostMapping("/add-price")
    public ResponseEntity<Object> addEventPrice(@RequestBody AddEventPriceRequest addEventPriceRequest, HttpServletRequest httpServletRequest) {
        return eventPriceService.addEventPrice(addEventPriceRequest, httpServletRequest);
    }

    @PutMapping("/update-price")
    public ResponseEntity<Object> updateEventPrice(@RequestBody UpdateEventPriceRequest updateEventPriceRequest, HttpServletRequest httpServletRequest) {
        return eventPriceService.updateEventPrice(updateEventPriceRequest, httpServletRequest);
    }

    @DeleteMapping("/delete-price")
    public ResponseEntity<Object> deleteEventPrice(@RequestParam Long eventPriceId, HttpServletRequest httpServletRequest) {
        DeleteEventPriceRequest deleteEventPriceRequest = new DeleteEventPriceRequest();
        deleteEventPriceRequest.setEventPriceId(eventPriceId);
        return eventPriceService.deleteEventPrice(deleteEventPriceRequest, httpServletRequest);
    }
}
