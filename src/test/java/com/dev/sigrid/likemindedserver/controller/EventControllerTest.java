package com.dev.sigrid.likemindedserver;

import com.dev.sigrid.likemindedserver.controller.EventController;
import com.dev.sigrid.likemindedserver.domain.Address;
import com.dev.sigrid.likemindedserver.domain.Event;
import com.dev.sigrid.likemindedserver.dto.EventDTO;
import com.dev.sigrid.likemindedserver.security.CustomUserDetailsService;
import com.dev.sigrid.likemindedserver.service.EventService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MimeTypeUtils;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties="spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        EventController.class
})
@Import(Config.class)
public class EventControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private EventController eventController;

    @Autowired
    private EventService eventService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(eventController)
                .build();
    }

    @Test
    public void getEventsReturnsOkAndExpectedJson() throws Exception {
        // Given
        Event event = new Event();
        event.setName("Event name1");
        event.setDescription("This is an event.");
        Address address = new Address();
        address.setAddressLine("12 some st");
        address.setCity("city");
        address.setPostcode("12345");
        address.setCountrycode("est");
        event.setAddress(address);

        List<EventDTO> allEvents = singletonList(EventDTO.to(event));
        doReturn(allEvents).when(eventService).getAllEvents();

        // When
        final ResultActions result = mockMvc.perform(
                get("/api/v1/events")
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        // Then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.length()").value(allEvents.size()));
        result.andExpect(jsonPath("$[?(@.name === 'Event name1')]").exists());
        result.andExpect(jsonPath("$[0].description", is("This is an event.")));
    }
}
