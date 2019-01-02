package com.dev.sigrid.likemindedserver.dto;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Data
public class CreateEventCommand {
    @NonNull
    @Min(3)
    @Max(45)
    private String name;
    @Min(10)
    @Max(200)
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
    @Max(214000) // 200x200px 72dpi
    private String picture;
}
