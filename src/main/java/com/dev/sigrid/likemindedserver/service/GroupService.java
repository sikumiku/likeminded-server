package com.dev.sigrid.likemindedserver.service;

import com.dev.sigrid.likemindedserver.domain.Group;
import com.dev.sigrid.likemindedserver.domain.GroupCategory;
import com.dev.sigrid.likemindedserver.domain.User;
import com.dev.sigrid.likemindedserver.dto.CreateGroupCommand;
import com.dev.sigrid.likemindedserver.dto.GroupDTO;
import com.dev.sigrid.likemindedserver.repository.CategoryRepository;
import com.dev.sigrid.likemindedserver.repository.GroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@Slf4j
public class GroupService {
    private GroupRepository groupRepository;
    private CategoryRepository categoryRepository;

    public GroupService(GroupRepository groupRepository, CategoryRepository categoryRepository) {
        this.groupRepository = groupRepository;
        this.categoryRepository = categoryRepository;
    }

    public GroupDTO createGroupForUser(CreateGroupCommand createGroupCommand, User user) {

        Group group = new Group();
        group.setName(createGroupCommand.getName());
        group.setDescription(createGroupCommand.getDescription());

        List<GroupCategory> groupCategories = new ArrayList<>();
        createGroupCommand.getCategories().forEach(category -> groupCategories.add(GroupCategory.builder()
                .group(group)
                .category(categoryRepository.findByName(category))
                .build()));
        group.setGroupCategories(groupCategories);

        Group result = groupRepository.save(group);

        return GroupDTO.to(result);
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

}
