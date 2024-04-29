package com.monguichet.catalog.Controller;


import com.monguichet.catalog.Entity.DTO.EventRequestDto;
import com.monguichet.catalog.Entity.DTO.EventResponseDto;
import com.monguichet.catalog.Entity.Event;
import com.monguichet.catalog.Repository.EventRepo;
import com.monguichet.catalog.Service.EventService;
import com.monguichet.catalog.Utils.MappingProfile;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/event")
@AllArgsConstructor
public class EventController {
private final EventService eventService;
 private final EventRepo eventRepo;

 // Search All
   @GetMapping("/")
    public ResponseEntity<List<EventResponseDto>> getAllEvent() {
        List<EventResponseDto> event = eventService.getAllEvent();
        return new ResponseEntity<>(event, HttpStatus.OK);
    }
// Search by pagination
  @GetMapping("/p")
  public List<EventResponseDto> getAllEventsPagination(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
      return eventService.getAllEventsPagination(page, size);
  }

    // Endpoint to create a new Event
    @PostMapping("/create")
    public ResponseEntity<EventResponseDto> createEvent(@RequestBody EventRequestDto eventDto) {
        EventResponseDto createdEvent = eventService.createEvent(eventDto);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }
    //Update Event
    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDto> updateEvent(@PathVariable Long id, @RequestBody EventRequestDto eventDto) {
        EventResponseDto updatedEvent = eventService.updateEvent(id, eventDto);
        return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
    }
    @GetMapping("/subCategory/{subCategoryId}")
    public ResponseEntity<List<EventResponseDto>> getEventsBySubCategoryId(@PathVariable Long subCategoryId) {
        List<EventResponseDto> events = eventService.getEventsBySubCategoryId(subCategoryId);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEventById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
// Chercher dans name
@GetMapping("/search")
public ResponseEntity<List<EventResponseDto>> searchEventsByName(@RequestParam("name") String keyword) {
    List<EventResponseDto> events = eventService.searchEventsByName(keyword);
    return new ResponseEntity<>(events, HttpStatus.OK);
}
// Chercher dans name ou description a keyword
@GetMapping("/search2")
public ResponseEntity<List<EventResponseDto>> searchEventsByKeyword(@RequestParam("keyword") String keyword) {
    List<EventResponseDto> events = eventService.searchEventsByNameOrDescription(keyword);
    return new ResponseEntity<>(events, HttpStatus.OK);
}

}
