package com.dev.sigrid.likemindedserver.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginCommand {
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
