package com.dev.sigrid.likemindedserver.dto;

import com.dev.sigrid.likemindedserver.domain.Address;
import com.dev.sigrid.likemindedserver.domain.Category;
import com.dev.sigrid.likemindedserver.domain.Game;
import com.dev.sigrid.likemindedserver.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class FullUserDTO {
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private Address address;
//    private Date birthday;
    private List<Category> categories;
    private List<Game> favoriteGames;
    private String imageBase64;

    public static FullUserDTO domainToDto(User user) {
        return new FullUserDTO(
                user.getUsername(),
                user.getEmail(),
                user.getFirstname(),
                user.getLastname(),
                user.getAddress(),
                user.getCategories(user),
                user.getFavoriteGames(user),
                user.getProfileImgFilePath()
        );
    }
}

