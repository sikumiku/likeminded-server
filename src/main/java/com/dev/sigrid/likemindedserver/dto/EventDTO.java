package com.dev.sigrid.likemindedserver.dto;

import com.dev.sigrid.likemindedserver.domain.Category;
import com.dev.sigrid.likemindedserver.domain.Event;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class EventDTO {

    private Long id;
    private String name;
    private String description;
    private Boolean openToPublic;
    private Boolean unlimitedParticipants;
    private Integer maxParticipants;
    private AddressDTO address;
    private List<Category> categories;
    private List<EventTimeDTO> eventTimes;
    private String imageBase64;

    public static EventDTO to(Event event) {
        return new EventDTO(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getOpenToPublic(),
                event.getUnlimitedParticipants(),
                event.getMaxParticipants(),
                AddressDTO.domainToDto(event.getAddress()),
                event.getCategories(event),
                event.getEventTimes().stream().map(EventTimeDTO::domainToDto).collect(Collectors.toList()),
                event.getImageBase64()
        );
    }
}

