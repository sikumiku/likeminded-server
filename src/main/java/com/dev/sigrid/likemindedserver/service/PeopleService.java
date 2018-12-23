package com.dev.sigrid.likemindedserver.service;

import com.dev.sigrid.likemindedserver.domain.Address;
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
        List<User> users = userRepository.findAll();
        List<PersonDTO> personDTOs = new ArrayList<>();
        users.forEach(user -> {
            Address address;
            if (user.getAddress() == null) {
                address = new Address();
                address.setAddressLine("");
                address.setCity("");
                address.setPostcode("");
                address.setCountrycode("");
                address.setUser(user);
                user.setAddress(address);
            }

            personDTOs.add(PersonDTO.to(user));
        });
        return personDTOs;
    }
}
