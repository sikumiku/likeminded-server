package com.dev.sigrid.likemindedserver.service;

import com.dev.sigrid.likemindedserver.domain.*;
import com.dev.sigrid.likemindedserver.dto.CreateGroupCommand;
import com.dev.sigrid.likemindedserver.dto.GroupDTO;
import com.dev.sigrid.likemindedserver.repository.CategoryRepository;
import com.dev.sigrid.likemindedserver.repository.GroupRepository;
import com.dev.sigrid.likemindedserver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
public class GroupService {
    private GroupRepository groupRepository;
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;

    public GroupService(GroupRepository groupRepository,
                        CategoryRepository categoryRepository,
                        UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public GroupDTO createGroupForUser(CreateGroupCommand createGroupCommand, User user) {

        Group group = new Group();
        group.setName(createGroupCommand.getName());
        group.setDescription(createGroupCommand.getDescription());
        group.setUser(user);

        List<GroupCategory> groupCategories = new ArrayList<>();
        createGroupCommand.getCategories().forEach(category -> groupCategories.add(GroupCategory.builder()
                .group(group)
                .category(categoryRepository.findByName(category))
                .build()));
        group.setGroupCategories(groupCategories);

        group.setImageFilePath(createGroupCommand.getPicture());

        Group result = groupRepository.save(group);

        return GroupDTO.to(result);
    }

    public List<GroupDTO> getGroupsForUser(User user) {
        List<Group> groups = groupRepository.findAllByUserId(user.getId());
        return convertGroupsToDtos(groups);
    }

    public List<GroupDTO> getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        List<GroupDTO> groupDTOs = new ArrayList<>();
        groups.forEach(group -> groupDTOs.add(GroupDTO.to(group)));
        return groupDTOs;
    }

    public GroupDTO updateGroup(CreateGroupCommand groupChanges, Group group) {
        group.setName(groupChanges.getName());
        group.setDescription(groupChanges.getDescription());
        Group updatedGroup = groupRepository.save(group);
        return GroupDTO.to(updatedGroup);
    }

    public GroupDTO addUsersToGroup(Group group, List<Long> userIds) {
        //clear duplicates
        Set<Long> userSet = new HashSet<>(userIds);
        userIds.clear();
        userIds.addAll(userSet);

        List<UserGroup> groupUsers = group.getUserGroups();
        List<User> users = groupUsers.stream()
                .map(UserGroup::getUser)
                .collect(Collectors.toList());
        userIds.forEach(userId -> {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (!users.contains(user)) {
                    groupUsers.add(UserGroup.builder()
                            .user(user)
                            .group(group)
                            .build());
                }
            }
        });

        group.setUserGroups(groupUsers);
        groupRepository.save(group);

        return GroupDTO.to(group);
    }

    private List<GroupDTO> convertGroupsToDtos(List<Group> groups) {
        List<GroupDTO> groupDTOs = new ArrayList<>();
        groups.forEach(group -> {
            groupDTOs.add(GroupDTO.to(group));
        });
        return groupDTOs;
    }

}
