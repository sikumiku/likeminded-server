package com.dev.sigrid.likemindedserver.dto;

import com.dev.sigrid.likemindedserver.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonDTO {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;

    public static PersonDTO to(User user) {
        return new PersonDTO(
                user.getId(),
                user.getUsername(),
                user.getFirstname(),
                user.getLastname()
        );
    }
}
