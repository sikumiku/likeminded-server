package com.dev.sigrid.likemindedserver.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "address")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 3, max = 45)
    private String addressLine;
    @NonNull
    @Size(min = 3, max = 45)
    private String city;
    @NonNull
    @Size(min = 5, max = 10)
    private String postcode;
    @NonNull
    @Size(min = 3, max = 3)
    private String countrycode;
    @Builder.Default
    private boolean active = true;
    @CreationTimestamp
    private LocalDateTime createdTime;
    @UpdateTimestamp
    private LocalDateTime updatedTime;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Address(String addressLine, String city, String postcode, String countrycode, User user) {
        this.addressLine = addressLine;
        this.city = city;
        this.postcode = postcode;
        this.countrycode = countrycode;
        this.user = user;
    }
}
