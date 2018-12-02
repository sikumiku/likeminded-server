package com.dev.sigrid.likemindedserver.controller;

import com.dev.sigrid.likemindedserver.domain.User;
import com.dev.sigrid.likemindedserver.dto.UserDTO;
import com.dev.sigrid.likemindedserver.repository.UserRepository;
import com.dev.sigrid.likemindedserver.security.CurrentUser;
import com.dev.sigrid.likemindedserver.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users/me")
    @PreAuthorize("hasRole('USER')")
    public UserDTO getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        User user = userRepository.getOne(currentUser.getId());
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
