package com.dev.sigrid.likemindedserver.service;

import com.dev.sigrid.likemindedserver.domain.User;
import com.dev.sigrid.likemindedserver.dto.PersonDTO;
import com.dev.sigrid.likemindedserver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@Slf4j
public class PeopleService {
    private UserRepository userRepository;

    public PeopleService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<PersonDTO> getAllPeople() {
        System.out.println("fetching users");
        List<User> users = userRepository.findAll();
        System.out.println(users);
        List<PersonDTO> personDTOs = new ArrayList<>();
        users.forEach(user -> {
            personDTOs.add(PersonDTO.to(user));
        });
        return personDTOs;
    }
}
