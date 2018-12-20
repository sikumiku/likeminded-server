package com.dev.sigrid.likemindedserver.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class CreateGroupCommand {
    private String name;
    private String description;
}
