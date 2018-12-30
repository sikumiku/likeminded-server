package com.dev.sigrid.likemindedserver.controller;

import com.dev.sigrid.likemindedserver.dto.PersonDTO;
import com.dev.sigrid.likemindedserver.repository.UserRepository;
import com.dev.sigrid.likemindedserver.service.PeopleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for anything people related
 * getPeople()
 */
@RestController
@RequestMapping("/api/v1")
public class PeopleController {
    private final Logger log = LoggerFactory.getLogger(PeopleController.class);
    private UserRepository userRepository;
    private PeopleService peopleService;

    public PeopleController(UserRepository userRepository, PeopleService peopleService) {
        this.userRepository = userRepository;
        this.peopleService = peopleService;
    }

    @GetMapping("/people")
    ResponseEntity<List<PersonDTO>> people() {
        log.info("Fetching people");
        List<PersonDTO> people = peopleService.getAllPeople();
        return ResponseEntity.ok(people);
    }
}
