package com.dev.sigrid.likemindedserver.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "event_time")
public class EventTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // secondOfDay, to parse:
    // LocalTime time = LocalTime.ofSecondOfDay(secondOfDay);
    // int secondOfDay =  LocalTime.parse("12:34:45").toSecondOfDay();
    private Long startTime;
    private Long endTime;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventTime )) return false;
        return id != null && id.equals(((EventTime) o).id);
    }
    @Override
    public int hashCode() {
        return 31;
    }
}
