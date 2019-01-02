package com.dev.sigrid.likemindedserver.dto;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CreateEventCommand {
    @NonNull
    @Size(min = 3, max = 45)
    private String name;
    @Size(min = 10, max = 200)
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
