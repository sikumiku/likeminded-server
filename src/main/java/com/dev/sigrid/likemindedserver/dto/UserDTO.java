package com.dev.sigrid.likemindedserver.dto;

import com.dev.sigrid.likemindedserver.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String imageBase64;

    public static UserDTO to(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstname(),
                user.getLastname(),
                user.getImageBase64()
        );
    }
}
