package com.dev.sigrid.likemindedserver.dto;

import com.dev.sigrid.likemindedserver.domain.Category;
import com.dev.sigrid.likemindedserver.domain.Event;
import com.dev.sigrid.likemindedserver.domain.EventTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
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
    private List<Category> categories;
//    private List<EventTimeDTO> eventTimes;

    public static EventDTO to(Event event) {
        return new EventDTO(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getOpenToPublic(),
                event.getUnlimitedParticipants(),
                event.getMaxParticipants(),
                event.getCategories(event)
//                event.getEventTimes().stream().map(EventTimeDTO::to).collect(Collectors.toList())
        );
    }
}

