package com.dev.sigrid.likemindedserver.controller;

import com.dev.sigrid.likemindedserver.domain.Event;
import com.dev.sigrid.likemindedserver.domain.Group;
import com.dev.sigrid.likemindedserver.domain.User;
import com.dev.sigrid.likemindedserver.dto.CreateGroupCommand;
import com.dev.sigrid.likemindedserver.dto.EventDTO;
import com.dev.sigrid.likemindedserver.dto.GroupDTO;
import com.dev.sigrid.likemindedserver.repository.GroupRepository;
import com.dev.sigrid.likemindedserver.repository.UserRepository;
import com.dev.sigrid.likemindedserver.security.CurrentUser;
import com.dev.sigrid.likemindedserver.security.UserPrincipal;
import com.dev.sigrid.likemindedserver.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Controller for anything group related
 * getAllGroups(), getGroup(Long), createGroup(CreateGroupCommand, UserPrincipal),
 * updateGroup(UpdateGroupCommand, Long, UserPrincipal), deleteGroup(Long, UserPrincipal)
 */
@RestController
@RequestMapping("/api/v1")
public class GroupController {
    private final Logger log = LoggerFactory.getLogger(GroupController.class);
    private GroupRepository groupRepository;
    private UserRepository userRepository;
    private GroupService groupService;

    public GroupController(GroupRepository groupRepository,
                           UserRepository userRepository,
                           GroupService groupService) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.groupService = groupService;
    }

    @GetMapping("/groups")
    ResponseEntity<List<GroupDTO>> groups() {
        System.out.println("fetching groups");
        List<GroupDTO> groups = groupService.getAllGroups();
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/groups/{id}")
    ResponseEntity<Group> getGroup(@PathVariable Long id) {
        Optional<Group> group = groupRepository.findById(id);
        return group.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/groups")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<GroupDTO> createGroup(@Valid @RequestBody CreateGroupCommand createGroupCommand,
                                         @CurrentUser UserPrincipal currentUser) throws URISyntaxException {
        log.info("Request to create group: {}", createGroupCommand);

        Optional<User> optionalUser = userRepository.findById(currentUser.getId());
        User user = null;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        // TODO: look if event by same name already exists

        GroupDTO groupDTO = groupService.createGroupForUser(createGroupCommand, user);

        return ResponseEntity.created(new URI("/api/v1/groups/" + groupDTO.getId()))
                .body(groupDTO);
    }

    @PutMapping("/groups/{id}/invite")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<GroupDTO> addUsersToGroup(@Valid @PathVariable Long id, @RequestParam("userIds") List<Long> userIds,
                                             @CurrentUser UserPrincipal currentUser) throws URISyntaxException {
        log.info("Request to add user with ids {} to event id {}", userIds, id);

        Group group;
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isPresent()) {
            group = optionalGroup.get();
        } else {
            return ResponseEntity.notFound().build();
        }

        Optional<User> optionalUser = userRepository.findById(currentUser.getId());
        User user = null;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }

        if (user != null && Objects.equals(group.getUser().getId(), user.getId())) {
            return ResponseEntity.ok(groupService.addUsersToGroup(group, userIds));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/groups/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<GroupDTO> updateGroup(@Valid @RequestBody CreateGroupCommand updateGroupCommand,
                                         @PathVariable Long id,
                                         @CurrentUser UserPrincipal currentUser) {
        log.info("Request to update group: {}", updateGroupCommand);
        Group group;
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isPresent()) {
            group = optionalGroup.get();
        } else {
            return ResponseEntity.notFound().build();
        }
        Optional<User> optionalUser = userRepository.findById(currentUser.getId());
        User user = null;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        if (user != null && Objects.equals(group.getUser().getId(), user.getId())) {
            return ResponseEntity.ok(groupService.updateGroup(updateGroupCommand, group));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/groups/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Group> deleteGroup(@PathVariable Long id,
                                             @CurrentUser UserPrincipal currentUser) {
        log.info("Request to delete group: {}", id);
        Optional<Group> optionalGroup = groupRepository.findById(id);
        Optional<User> optionalUser = userRepository.findById(currentUser.getId());
        if (optionalGroup.isPresent()) {
            if (optionalUser.isPresent()) {
                if (optionalUser.get().getId() == optionalGroup.get().getUser().getId()) {
                    groupRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            }
        }
        return ResponseEntity.notFound().build();
    }
}
