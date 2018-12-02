package com.dev.sigrid.likemindedserver;

import com.dev.sigrid.likemindedserver.domain.Address;
import com.dev.sigrid.likemindedserver.domain.Event;
import com.dev.sigrid.likemindedserver.domain.Role;
import com.dev.sigrid.likemindedserver.domain.User;
import com.dev.sigrid.likemindedserver.repository.AddressRepository;
import com.dev.sigrid.likemindedserver.repository.EventRepository;
import com.dev.sigrid.likemindedserver.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class Initializer implements CommandLineRunner {
    private final EventRepository eventRepository;
    private final AddressRepository addressRepository;
    private final RoleRepository roleRepository;

    public Initializer(EventRepository eventRepository, AddressRepository addressRepository, RoleRepository roleRepository) {
        this.eventRepository = eventRepository;
        this.addressRepository = addressRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... strings) {
        Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
        Optional<Role> adminRole = roleRepository.findByName("ROLE_ADMIN");

        if (!userRole.isPresent()) {
            Role role = Role.builder()
                    .name("ROLE_USER")
                    .description("Regular user")
                    .build();
            roleRepository.save(role);
        }

        if (!adminRole.isPresent()) {
            Role role = Role.builder()
                    .name("ROLE_ADMIN")
                    .description("Admin user")
                    .build();
            roleRepository.save(role);
        }
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

        roleRepository.findAll().forEach(System.out::println);
    }
}
