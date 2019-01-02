package com.dev.sigrid.likemindedserver.dto;

import com.dev.sigrid.likemindedserver.domain.Category;
import com.dev.sigrid.likemindedserver.domain.Group;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GroupDTO {
    private Long id;
    private String name;
    private String description;
    private List<Category> categories;
    private String imageBase64;

    public static GroupDTO to(Group group) {
        return new GroupDTO(
                group.getId(),
                group.getName(),
                group.getDescription(),
                group.getCategories(group),
                group.getImageBase64()
        );
    }
}
