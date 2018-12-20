package com.dev.sigrid.likemindedserver.service;

import com.dev.sigrid.likemindedserver.domain.*;
import com.dev.sigrid.likemindedserver.dto.AddressDTO;
import com.dev.sigrid.likemindedserver.dto.CreateEventCommand;
import com.dev.sigrid.likemindedserver.dto.EventDTO;
import com.dev.sigrid.likemindedserver.dto.UpdateEventCommand;
import com.dev.sigrid.likemindedserver.repository.CategoryRepository;
import com.dev.sigrid.likemindedserver.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@Slf4j
public class EventService {

    private EventRepository eventRepository;
    private CategoryRepository categoryRepository;

    public EventService(EventRepository eventRepository, CategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
    }

    public EventDTO createEventForUser(CreateEventCommand createEventCommand, User user) {

        Event event = new Event();
        event.setName(createEventCommand.getName());
        event.setDescription(createEventCommand.getDescription());
        event.setOpenToPublic(createEventCommand.getOpenToPublic());
        event.setUnlimitedParticipants(createEventCommand.getUnlimitedParticipants());
        event.setMaxParticipants(createEventCommand.getMaxParticipants());
        event.addAddress(AddressDTO.dtoToDomain(createEventCommand.getAddress(), user));
        event.setUser(user);

        List<EventCategory> eventCategories = new ArrayList<>();
        createEventCommand.getCategories().forEach(category -> eventCategories.add(EventCategory.builder()
                .event(event)
                .category(categoryRepository.findByName(category))
                .build()));
        event.setEventCategories(eventCategories);

        Event result = eventRepository.save(event);

        return EventDTO.to(result);
    }

    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        List<EventDTO> eventDTOs = new ArrayList<>();
        events.forEach(event -> {
            Address address;
            if (event.getAddress() == null) {
                log.info("Event no " + event.getId() + " address is null;");
                address = new Address();
                address.setAddressLine("");
                address.setCity("");
                address.setPostcode("");
                address.setCountrycode("");
                address.setEvent(event);
                event.setAddress(address);
            }

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
