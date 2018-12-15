package com.dev.sigrid.likemindedserver.dto;

import com.dev.sigrid.likemindedserver.domain.EventTime;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventTimeDTO {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @Builder
    public EventTimeDTO(LocalDateTime startDateTime,
                       LocalDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    static EventTimeDTO to(EventTime eventTime) {
        // secondOfDay, to parse:
        // LocalTime time = LocalTime.ofSecondOfDay(secondOfDay);
        // int secondOfDay =  LocalTime.parse("12:34:45").toSecondOfDay();
        return EventTimeDTO.builder()
                .startDateTime(LocalDateTime.of(eventTime.getStartDate(), LocalTime.ofSecondOfDay(eventTime.getStartTime())))
                .endDateTime(LocalDateTime.of(eventTime.getEndDate(), LocalTime.ofSecondOfDay(eventTime.getEndTime()))).build();
    }
}
