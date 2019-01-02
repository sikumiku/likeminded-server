package com.dev.sigrid.likemindedserver.dto;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
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
