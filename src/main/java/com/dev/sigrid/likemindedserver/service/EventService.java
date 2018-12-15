package com.dev.sigrid.likemindedserver.service;

import com.dev.sigrid.likemindedserver.domain.Event;
import com.dev.sigrid.likemindedserver.domain.User;
import com.dev.sigrid.likemindedserver.dto.CreateEventCommand;
import com.dev.sigrid.likemindedserver.dto.EventDTO;
import com.dev.sigrid.likemindedserver.dto.UpdateEventCommand;
import com.dev.sigrid.likemindedserver.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EventService {

    private EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventDTO createEventForUser(CreateEventCommand createEventCommand, User user) {

        Event event = new Event();
        event.setName(createEventCommand.getName());
        event.setDescription(createEventCommand.getDescription());
        event.setOpenToPublic(createEventCommand.getOpenToPublic());
        event.setUnlimitedParticipants(createEventCommand.getUnlimitedParticipants());
        event.setMaxParticipants(createEventCommand.getMaxParticipants());
        event.setUser(user);

        Event result = eventRepository.save(event);

        return EventDTO.to(result);
    }

    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        List<EventDTO> eventDTOs = new ArrayList<>();
        events.forEach(event -> {
            eventDTOs.add(EventDTO.to(event));
        });
        return eventDTOs;
    }

    public EventDTO updateEvent(UpdateEventCommand eventChanges, Event event) {
        event.setName(eventChanges.getName());
        event.setDescription(eventChanges.getDescription());
        event.setOpenToPublic(eventChanges.getOpenToPublic());
        event.setMaxParticipants(eventChanges.getMaxParticipants());
        event.setUnlimitedParticipants(eventChanges.getUnlimitedParticipants());

        Event updatedEvent = eventRepository.save(event);
        return EventDTO.to(updatedEvent);
    }

}
