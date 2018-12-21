package com.dev.sigrid.likemindedserver.controller;

import com.dev.sigrid.likemindedserver.service.EventService;
import com.dev.sigrid.likemindedserver.domain.Event;
import com.dev.sigrid.likemindedserver.domain.User;
import com.dev.sigrid.likemindedserver.dto.CreateEventCommand;
import com.dev.sigrid.likemindedserver.dto.EventDTO;
import com.dev.sigrid.likemindedserver.dto.UpdateEventCommand;
import com.dev.sigrid.likemindedserver.repository.EventRepository;
import com.dev.sigrid.likemindedserver.repository.UserRepository;
import com.dev.sigrid.likemindedserver.security.CurrentUser;
import com.dev.sigrid.likemindedserver.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class EventController {

    private final Logger log = LoggerFactory.getLogger(EventController.class);
    private EventRepository eventRepository;
    private UserRepository userRepository;
    private EventService eventService;

    public EventController(EventRepository eventRepository,
                           UserRepository userRepository,
                           EventService eventService) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.eventService = eventService;
    }

    @GetMapping("/events")
    ResponseEntity<List<EventDTO>> events() {
        System.out.println("fetching events");
        List<EventDTO> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/events/{id}")
    ResponseEntity<Event> getEvent(@PathVariable Long id) {
        Optional<Event> event = eventRepository.findById(id);
        return event.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/events")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<EventDTO> createEvent(@Valid @RequestBody CreateEventCommand createEventCommand,
                                         @CurrentUser UserPrincipal currentUser) throws URISyntaxException {
        log.info("Request to create event: {}", createEventCommand);

        Optional<User> optionalUser = userRepository.findById(currentUser.getId());
        User user = null;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        // TODO: look if event by same name already exists

        EventDTO eventDTO = eventService.createEventForUser(createEventCommand, user);

        return ResponseEntity.created(new URI("/api/v1/event/" + eventDTO.getId()))
                .body(eventDTO);
    }

    @PutMapping("/events/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<EventDTO> updateEvent(@Valid @RequestBody UpdateEventCommand updateEventCommand,
                                         @PathVariable Long id,
                                         @CurrentUser UserPrincipal currentUser) {
        log.info("Request to update event: {}", updateEventCommand);
        // fetch event, does it exist?
        Event event;
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            event = optionalEvent.get();
        } else {
            return ResponseEntity.notFound().build();
        }
        // if yes, check if user is also the owner of the event
        Optional<User> optionalUser = userRepository.findById(currentUser.getId());
        User user = null;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        if (user != null && Objects.equals(event.getUser().getId(), user.getId())) {
            return ResponseEntity.ok(eventService.updateEvent(updateEventCommand, event));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/events/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Event> deleteEvent(@PathVariable Long id,
                                             @CurrentUser UserPrincipal currentUser) {
        log.info("Request to delete event: {}", id);
        Optional<Event> optionalEvent = eventRepository.findById(id);
        Optional<User> optionalUser = userRepository.findById(currentUser.getId());
        if (optionalEvent.isPresent()) {
            if (optionalUser.isPresent()) {
                if (Objects.equals(optionalUser.get().getId(), optionalEvent.get().getUser().getId())) {
                    eventRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            }
        }
        return ResponseEntity.notFound().build();
    }
}
