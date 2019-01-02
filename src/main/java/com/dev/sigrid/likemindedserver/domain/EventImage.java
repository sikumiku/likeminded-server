package com.dev.sigrid.likemindedserver.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;

@Data
@NoArgsConstructor
@Entity
@Table(name = "event_image")
public class EventImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Max(40)
    private String imageName;
    @Max(214000) // 200px by 200px at 72dpi
    private String imageBase64;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventImage )) return false;
        return id != null && id.equals(((EventImage) o).id);
    }
    @Override
    public int hashCode() {
        return 31;
    }
}
