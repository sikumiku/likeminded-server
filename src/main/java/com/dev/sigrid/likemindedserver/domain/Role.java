package com.dev.sigrid.likemindedserver.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "role")
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;
    private String description;
    private boolean active;
    @CreationTimestamp
    private LocalDateTime createdTime;
    @UpdateTimestamp
    private LocalDateTime updatedTime;

    @OneToMany(
            mappedBy = "role",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UserRole> userRoles = new ArrayList<>();

}

