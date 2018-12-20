package com.dev.sigrid.likemindedserver.dto;

import lombok.Data;

@Data
public class UpdateUserCommand {
    private String username;
    private String firstname;
    private String lastname;
}
