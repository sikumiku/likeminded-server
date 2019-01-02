package com.dev.sigrid.likemindedserver.service;

import com.dev.sigrid.likemindedserver.domain.*;
import com.dev.sigrid.likemindedserver.dto.*;
import com.dev.sigrid.likemindedserver.repository.CategoryRepository;
import com.dev.sigrid.likemindedserver.repository.EventRepository;
import com.dev.sigrid.likemindedserver.repository.GroupRepository;
import com.dev.sigrid.likemindedserver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
public class EventService {

    private EventRepository eventRepository;
    private CategoryRepository categoryRepository;
    private GroupRepository groupRepository;
    private UserRepository userRepository;

    public EventService(EventRepository eventRepository,
                        CategoryRepository categoryRepository,
                        GroupRepository groupRepository,
                        UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
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

        event.setImageBase64(createEventCommand.getPicture());

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

    public EventDTO updateEvent(UpdateEventCommand eventChanges, Event event) {
        event.setName(eventChanges.getName());
        event.setDescription(eventChanges.getDescription());
        event.setOpenToPublic(eventChanges.getOpenToPublic());
        event.setMaxParticipants(eventChanges.getMaxParticipants());
        event.setUnlimitedParticipants(eventChanges.getUnlimitedParticipants());

        Event updatedEvent = eventRepository.save(event);
        return EventDTO.to(updatedEvent);
    }

    public EventDTO addUsersToEvent(Event event, List<Long> userIds, List<Long> groupIds) {
        //clear duplicates
        Set<Long> userSet = new HashSet<>(userIds);
        userIds.clear();
        userIds.addAll(userSet);
        Set<Long> groupSet = new HashSet<>(groupIds);
        groupIds.clear();
        groupIds.addAll(groupSet);

        //get userIds from groups
        List<Group> groups = groupRepository.findAllById(groupIds);
        groups.forEach(group -> userIds.add(group.getUser().getId()));

        List<UserEvent> eventUsers = event.getUserEvents();
        List<User> users = eventUsers.stream()
                .map(UserEvent::getUser)
                .collect(Collectors.toList());
        userIds.forEach(userId -> {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (!users.contains(user)) {
                    eventUsers.add(UserEvent.builder()
                            .user(user)
                            .event(event)
                            .build());
                }
            }
        });

        event.setUserEvents(eventUsers);
        eventRepository.save(event);

        return EventDTO.to(event);
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

}
