package com.dev.sigrid.likemindedserver.service;

import com.dev.sigrid.likemindedserver.domain.*;
import com.dev.sigrid.likemindedserver.dto.*;
import com.dev.sigrid.likemindedserver.repository.CategoryRepository;
import com.dev.sigrid.likemindedserver.repository.FavoriteGameRepository;
import com.dev.sigrid.likemindedserver.repository.GameRepository;
import com.dev.sigrid.likemindedserver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
public class UserService {
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private GameRepository gameRepository;
    private FavoriteGameRepository favoriteGameRepository;

    public UserService(UserRepository userRepository,
                       CategoryRepository categoryRepository,
                       GameRepository gameRepository,
                       FavoriteGameRepository favoriteGameRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.favoriteGameRepository = favoriteGameRepository;
    }

    public FullUserDTO getFullUser(User user) {
        return FullUserDTO.domainToDto(user);
    }

    public FullUserDTO updateUser(UpdateUserCommand updateUserCommand, User user) {
        user.setUsername(updateUserCommand.getUsername());
        user.setFirstname(updateUserCommand.getFirstname());
        user.setLastname(updateUserCommand.getLastname());

        user.setAddress(AddressDTO.dtoToDomain(updateUserCommand.getAddress(), user));

        if (updateUserCommand.getCategories().size() > 0) {
            List<UserCategory> newCategories = new ArrayList<>();
            updateUserCommand.getCategories().forEach(category -> {
                newCategories.add(UserCategory.builder()
                        .user(user)
                        .category(categoryRepository.findByName(category))
                        .build());
            });
            user.setCategories(newCategories);
        }

        List<GameDTO> newUserEnteredGames = updateUserCommand.getFavoriteGames().stream()
                .filter(gameInput -> (gameRepository.findByName(gameInput.getName()) == null))
                .collect(Collectors.toList());
        newUserEnteredGames.forEach(game -> gameRepository.save(GameDTO.dtoToDomain(game)));
        gameRepository.flush();

        List<FavoriteGame> games = user.getFavoriteGames();
        List<String> existingGames = new ArrayList<>();
        games.forEach(game -> {
            existingGames.add(game.getGame().getName());
        });

        updateUserCommand.getFavoriteGames().forEach(gameInput -> {
            Game game = gameRepository.findByName(gameInput.getName());
            FavoriteGame favGame = new FavoriteGame(game, user);
            if (!existingGames.contains(game.getName())) {
                games.add(favGame);
            }
        });
        user.setFavoriteGames(games);

        User updatedUser = userRepository.save(user);
        return FullUserDTO.domainToDto(updatedUser);
    }

    public FullUserDTO deleteFavoriteGame(String gameName, User user) {
        Game game = gameRepository.findByName(gameName);
        List<FavoriteGame> favoriteGames = user.getFavoriteGames();
        FavoriteGame objectToBeDeleted = null;
        for(FavoriteGame favoriteGame : favoriteGames) {
            if (favoriteGame.getGame().equals(game)) {
                objectToBeDeleted = favoriteGame;
            }
        }
        if (objectToBeDeleted != null) {
            favoriteGameRepository.delete(objectToBeDeleted.getId());
        }
        User updatedUser = userRepository.getOne(user.getId());
        return FullUserDTO.domainToDto(updatedUser);
    }
}
