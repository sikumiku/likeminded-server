package com.dev.sigrid.likemindedserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    @Builder
    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
