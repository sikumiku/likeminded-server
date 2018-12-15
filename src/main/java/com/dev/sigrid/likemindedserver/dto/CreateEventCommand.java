package com.dev.sigrid.likemindedserver.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateEventCommand {
    private String name;
    private String description;
    private Boolean openToPublic;
    private Boolean unlimitedParticipants;
    private Integer maxParticipants;
//    private List<CreateEventTimeCommand> eventTimes;
}
