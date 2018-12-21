package com.dev.sigrid.likemindedserver.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateGroupCommand {
    private String name;
    private String description;
    private List<String> categories;
}
