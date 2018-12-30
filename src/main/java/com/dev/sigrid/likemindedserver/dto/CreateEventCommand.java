package com.dev.sigrid.likemindedserver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateEventCommand {
    private String name;
    private String description;
    private Boolean openToPublic;
    private Boolean unlimitedParticipants;
    private Integer maxParticipants;
    private AddressDTO address;
    private List<String> categories;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private String picture;
}
