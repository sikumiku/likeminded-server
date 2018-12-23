package com.dev.sigrid.likemindedserver.controller;

import com.dev.sigrid.likemindedserver.domain.User;
import com.dev.sigrid.likemindedserver.dto.*;
import com.dev.sigrid.likemindedserver.repository.UserRepository;
import com.dev.sigrid.likemindedserver.security.CurrentUser;
import com.dev.sigrid.likemindedserver.security.UserPrincipal;
import com.dev.sigrid.likemindedserver.service.EventService;
import com.dev.sigrid.likemindedserver.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;
    private final UserService userService;
    private final EventService eventService;

    @Autowired
    public UserController(UserRepository userRepository,
                          UserService userService,
                          EventService eventService)
    {
        this.userRepository = userRepository;
        this.userService = userService;
        this.eventService = eventService;
    }

    @GetMapping("/users/me")
    @PreAuthorize("hasRole('ROLE_USER')")
    public UserDTO getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        User user = userRepository.getOne(currentUser.getId());
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstname(),
                user.getLastname()
        );
    }

    @GetMapping("/users/me/full")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<FullUserDTO> getFullCurrentUserData(@CurrentUser UserPrincipal currentUser) {
        User user = userRepository.getOne(currentUser.getId());
        FullUserDTO result = userService.getFullUser(user);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/users/me/events")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<EventDTO>> getEventsForCurrentUser(@CurrentUser UserPrincipal currentUser) {
        User user = userRepository.getOne(currentUser.getId());
        List<EventDTO> result = eventService.getEventsForUser(user);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/users/me/groups")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<GroupDTO>> getGroupsForCurrentUser(@CurrentUser UserPrincipal currentUser) {
        User user = userRepository.getOne(currentUser.getId());
        List<GroupDTO> result = eventService.getGroupsForUser(user);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<FullUserDTO> updateUser(@Valid @RequestBody UpdateUserCommand updateUserCommand,
                                        @PathVariable Long id,
                                        @CurrentUser UserPrincipal currentUser) {
        log.info("Request to update group: {}", updateUserCommand);
        User updateableUser;
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            updateableUser = optionalUser.get();
        } else {
            return ResponseEntity.notFound().build();
        }

        Optional<User> loggedInOptionalUser = userRepository.findById(currentUser.getId());
        User loggedInUser = null;
        if (loggedInOptionalUser.isPresent()) {
            loggedInUser = loggedInOptionalUser.get();
        }

        if (loggedInUser != null && Objects.equals(updateableUser.getId(), loggedInUser.getId())) {
            return ResponseEntity.ok(userService.updateUser(updateUserCommand, updateableUser));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/users/me/favoriteGames")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<FullUserDTO> deleteFavoriteGame(@RequestParam("gameName") String gameName,
                                             @CurrentUser UserPrincipal currentUser) {
        log.info("Request to delete favorite game: {}", gameName);
        User user = userRepository.getOne(currentUser.getId());
        return ResponseEntity.ok(userService.deleteFavoriteGame(gameName, user));
    }
}
