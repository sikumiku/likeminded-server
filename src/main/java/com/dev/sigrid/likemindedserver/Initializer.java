package com.dev.sigrid.likemindedserver;

import com.dev.sigrid.likemindedserver.domain.Address;
import com.dev.sigrid.likemindedserver.domain.Event;
import com.dev.sigrid.likemindedserver.domain.User;
import com.dev.sigrid.likemindedserver.repository.AddressRepository;
import com.dev.sigrid.likemindedserver.repository.EventRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Stream;

@Component
public class Initializer implements CommandLineRunner {
    private final EventRepository eventRepository;
    private final AddressRepository addressRepository;

    public Initializer(EventRepository eventRepository, AddressRepository addressRepository) {
        this.eventRepository = eventRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public void run(String... strings) {
//        Stream.of("Denver JUG", "Utah JUG", "Seattle JUG",
//                "Richmond JUG").forEach(name ->
//                eventRepository.save(new Event(name))
//        );
//
//        Event event = eventRepository.findByName("Denver JUG");
//        event.setDescription("Some description");
//        Address address = Address.builder()
//                .id(Long.parseLong("1"))
//                .addressLine("12 Some Address")
//                .postCode("12345")
//                .city("Some City")
//                .countryCode("usa")
//                .createdTime(LocalDateTime.now())
//                .updatedTime(LocalDateTime.now())
//                .build();
//        event.setAddress(address);
//        address.setEvent(event);
//        eventRepository.save(event);

        eventRepository.findAll().forEach(System.out::println);
    }
}
