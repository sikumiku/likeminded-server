package com.dev.sigrid.likemindedserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "event")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
//    @Min(3)
//    @Max(45)
    private String name;
    private String description;
    private Boolean openToPublic;
    private String photoFilePath;
    private Boolean unlimitedParticipants;
    private Integer maxParticipants;
    private boolean active = true;
    @CreationTimestamp
    private LocalDateTime createdTime;
    @UpdateTimestamp
    private LocalDateTime updatedTime;

    @OneToOne(
            cascade =  CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "event")
    private Address address;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(
            mappedBy = "event",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<EventCategory> eventCategories = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(
            mappedBy = "event",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UserEvent> userEvents = new ArrayList<>();

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "event",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<EventImage> images = new LinkedHashSet<>();

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "event",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<EventTime> eventTimes = new LinkedHashSet<>();

    public List<Category> getCategories(Event event) {
        List<EventCategory> eventCategories = event.getEventCategories();
        List<Category> categories = new ArrayList<>();
        eventCategories.forEach(eventCategory -> {
            categories.add(eventCategory.getCategory());
        });
        return categories;
    }

    public void addImage(EventImage image) {
        images.add(image);
        image.setEvent(this);
    }

    public void removeImage(EventImage image) {
        images.remove(image);
        image.setEvent(null);
    }

    public void addAddress(Address address) {
        address.setEvent(this);
        this.address = address;
    }

}
