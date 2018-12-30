package com.dev.sigrid.likemindedserver;

import com.dev.sigrid.likemindedserver.repository.EventRepository;
import com.dev.sigrid.likemindedserver.repository.UserRepository;
import com.dev.sigrid.likemindedserver.service.EventService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
class Config {

    @Bean
    public EventService eventService() {
        return Mockito.mock(EventService.class);
    }

    @Bean
    public EventRepository eventRepository() {
        return Mockito.mock(EventRepository.class);
    }

    @Bean
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }
}
