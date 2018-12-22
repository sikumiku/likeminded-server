package com.dev.sigrid.likemindedserver.service;

import com.dev.sigrid.likemindedserver.domain.*;
import com.dev.sigrid.likemindedserver.dto.GameDTO;
import com.dev.sigrid.likemindedserver.dto.UpdateUserCommand;
import com.dev.sigrid.likemindedserver.dto.UserDTO;
import com.dev.sigrid.likemindedserver.repository.CategoryRepository;
import com.dev.sigrid.likemindedserver.repository.GameRepository;
import com.dev.sigrid.likemindedserver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
public class UserService {
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private GameRepository gameRepository;

    public UserService(UserRepository userRepository,
                       CategoryRepository categoryRepository,
                       GameRepository gameRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    public UserDTO updateUser(UpdateUserCommand updateUserCommand, User user) {
        user.setUsername(updateUserCommand.getUsername());
        user.setFirstname(updateUserCommand.getFirstname());
        user.setLastname(updateUserCommand.getLastname());

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

        updateUserCommand.getFavoriteGames().forEach(gameInput -> {
            FavoriteGame favGame = new FavoriteGame(gameRepository.findByName(gameInput.getName()), user);
            if (!games.contains(favGame)) {
                games.add(favGame);
            }
        });
        user.setFavoriteGames(games);

        User updatedUser = userRepository.save(user);
        return UserDTO.to(updatedUser);
    }
}
