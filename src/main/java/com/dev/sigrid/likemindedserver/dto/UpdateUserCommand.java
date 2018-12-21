package com.dev.sigrid.likemindedserver.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateUserCommand {
    private String username;
    private String firstname;
    private String lastname;
    private List<String> categories;
}
