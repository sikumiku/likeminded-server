package com.dev.sigrid.likemindedserver.controller;

import com.dev.sigrid.likemindedserver.domain.User;
import com.dev.sigrid.likemindedserver.dto.UpdateUserCommand;
import com.dev.sigrid.likemindedserver.dto.UserDTO;
import com.dev.sigrid.likemindedserver.repository.UserRepository;
import com.dev.sigrid.likemindedserver.security.CurrentUser;
import com.dev.sigrid.likemindedserver.security.UserPrincipal;
import com.dev.sigrid.likemindedserver.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository,
                          UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
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

    @PutMapping("/users/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UpdateUserCommand updateUserCommand,
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
}
