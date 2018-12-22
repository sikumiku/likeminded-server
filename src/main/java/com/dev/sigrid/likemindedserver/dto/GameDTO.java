package com.dev.sigrid.likemindedserver.dto;

import com.dev.sigrid.likemindedserver.domain.Game;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameDTO {
    private String name;
    private String description;

    public static GameDTO domainToDto(Game game) {
        return new GameDTO(
                game.getName(),
                game.getDescription()
        );
    }

    public static Game dtoToDomain(GameDTO game) {
        return new Game(
                game.getName(),
                game.getDescription()
        );
    }
}
