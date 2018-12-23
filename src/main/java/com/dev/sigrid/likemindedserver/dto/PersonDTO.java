package com.dev.sigrid.likemindedserver.dto;

import com.dev.sigrid.likemindedserver.domain.Category;
import com.dev.sigrid.likemindedserver.domain.FavoriteGame;
import com.dev.sigrid.likemindedserver.domain.Game;
import com.dev.sigrid.likemindedserver.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PersonDTO {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private AddressDTO address;
    private List<Category> categories;
    private List<Game> favoriteGames;

    public static PersonDTO to(User user) {
        return new PersonDTO(
                user.getId(),
                user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                AddressDTO.domainToDto(user.getAddress()),
                user.getCategories(user),
                user.getFavoriteGames(user)
        );
    }
}
