package com.dev.sigrid.likemindedserver.controller;

import com.dev.sigrid.likemindedserver.domain.Role;
import com.dev.sigrid.likemindedserver.domain.User;
import com.dev.sigrid.likemindedserver.dto.ApiResponse;
import com.dev.sigrid.likemindedserver.dto.JwtAuthenticationResponse;
import com.dev.sigrid.likemindedserver.dto.LoginCommand;
import com.dev.sigrid.likemindedserver.dto.SignupCommand;
import com.dev.sigrid.likemindedserver.repository.RoleRepository;
import com.dev.sigrid.likemindedserver.repository.UserRepository;
import com.dev.sigrid.likemindedserver.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginCommand loginCommand) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginCommand.getEmail(),
                        loginCommand.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupCommand signUpCommand) {
        if (userRepository.existsByEmail(signUpCommand.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User(signUpCommand.getUsername(),
                signUpCommand.getEmail(),
                signUpCommand.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/users/{id}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}
