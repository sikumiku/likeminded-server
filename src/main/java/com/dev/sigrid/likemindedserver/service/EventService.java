package com.dev.sigrid.likemindedserver.service;

import com.dev.sigrid.likemindedserver.domain.*;
import com.dev.sigrid.likemindedserver.dto.*;
import com.dev.sigrid.likemindedserver.repository.CategoryRepository;
import com.dev.sigrid.likemindedserver.repository.EventRepository;
import com.dev.sigrid.likemindedserver.repository.GroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service
@Slf4j
public class EventService {

    private EventRepository eventRepository;
    private CategoryRepository categoryRepository;
    private GroupRepository groupRepository;

    public EventService(EventRepository eventRepository,
                        CategoryRepository categoryRepository,
                        GroupRepository groupRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.groupRepository = groupRepository;
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

        Set<EventTime> times = new HashSet<>();
        ZonedDateTime startDate = ZonedDateTime.parse(createEventCommand.getStartDate());
        ZonedDateTime startTime = ZonedDateTime.parse(createEventCommand.getStartTime());
        ZonedDateTime endDate = ZonedDateTime.parse(createEventCommand.getEndDate());
        ZonedDateTime endTime = ZonedDateTime.parse(createEventCommand.getEndTime());
        EventTime eventTime = new EventTime();
        eventTime.setStartDate(startDate.toLocalDate());
        eventTime.setEndDate(endDate.toLocalDate());
        eventTime.setStartTime((long) startTime.toLocalTime().toSecondOfDay());
        eventTime.setEndTime((long) endTime.toLocalTime().toSecondOfDay());
        eventTime.setEvent(event);
        times.add(eventTime);
        event.setEventTimes(times);

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
        return convertEventsToDtos(events);
    }

    public List<EventDTO> getEventsForUser(User user) {
        List<Event> events = eventRepository.findAllByUserId(user.getId());
        return convertEventsToDtos(events);
    }

    public List<GroupDTO> getGroupsForUser(User user) {
        List<Group> groups = groupRepository.findAllByUserId(user.getId());
        return convertGroupsToDtos(groups);
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

    private List<EventDTO> convertEventsToDtos(List<Event> events) {
        List<EventDTO> eventDTOs = new ArrayList<>();
        events.forEach(event -> {
            Address address;
            if (event.getAddress() == null) {
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

    private List<GroupDTO> convertGroupsToDtos(List<Group> groups) {
        List<GroupDTO> groupDTOs = new ArrayList<>();
        groups.forEach(group -> {
            groupDTOs.add(GroupDTO.to(group));
        });
        return groupDTOs;
    }

}
