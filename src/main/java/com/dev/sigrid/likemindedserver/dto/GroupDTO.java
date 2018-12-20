package com.dev.sigrid.likemindedserver.dto;

import com.dev.sigrid.likemindedserver.domain.Group;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupDTO {
    private Long id;
    private String name;
    private String description;

    public static GroupDTO to(Group group) {
        return new GroupDTO(
                group.getId(),
                group.getName(),
                group.getDescription()
        );
    }
}
