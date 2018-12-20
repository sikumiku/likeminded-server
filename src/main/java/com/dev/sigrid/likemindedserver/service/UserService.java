package com.dev.sigrid.likemindedserver.service;

import com.dev.sigrid.likemindedserver.domain.User;
import com.dev.sigrid.likemindedserver.dto.UpdateUserCommand;
import com.dev.sigrid.likemindedserver.dto.UserDTO;
import com.dev.sigrid.likemindedserver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@Slf4j
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO updateUser(UpdateUserCommand updateUserCommand, User user) {
        user.setUsername(updateUserCommand.getUsername());
        user.setFirstname(updateUserCommand.getFirstname());
        user.setLastname(updateUserCommand.getLastname());
        User updatedUser = userRepository.save(user);
        return UserDTO.to(updatedUser);
    }
}
