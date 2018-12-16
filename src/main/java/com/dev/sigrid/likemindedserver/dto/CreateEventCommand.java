package com.dev.sigrid.likemindedserver.dto;

import com.dev.sigrid.likemindedserver.domain.Category;
import lombok.Data;

import java.util.List;

@Data
public class CreateEventCommand {
    private String name;
    private String description;
    private Boolean openToPublic;
    private Boolean unlimitedParticipants;
    private Integer maxParticipants;
    private List<String> categories;
//    private List<CreateEventTimeCommand> eventTimes;
}
