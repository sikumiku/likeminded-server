package com.dev.sigrid.likemindedserver.service;

import com.dev.sigrid.likemindedserver.domain.GroupCategory;
import com.dev.sigrid.likemindedserver.domain.User;
import com.dev.sigrid.likemindedserver.domain.UserCategory;
import com.dev.sigrid.likemindedserver.dto.UpdateUserCommand;
import com.dev.sigrid.likemindedserver.dto.UserDTO;
import com.dev.sigrid.likemindedserver.repository.CategoryRepository;
import com.dev.sigrid.likemindedserver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@Slf4j
public class UserService {
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;

    public UserService(UserRepository userRepository, CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
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

        User updatedUser = userRepository.save(user);
        return UserDTO.to(updatedUser);
    }
}
