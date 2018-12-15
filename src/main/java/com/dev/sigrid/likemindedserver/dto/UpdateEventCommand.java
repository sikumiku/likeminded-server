package com.dev.sigrid.likemindedserver.dto;

import lombok.Data;

@Data
public class UpdateEventCommand {
    private String name;
    private String description;
    private Boolean openToPublic;
    private Boolean unlimitedParticipants;
    private Integer maxParticipants;
}
