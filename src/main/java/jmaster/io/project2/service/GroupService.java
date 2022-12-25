package jmaster.io.project2.service;

import jmaster.io.project2.dto.GroupDTO;
import jmaster.io.project2.dto.PageDTO;
import jmaster.io.project2.dto.UserDTO;
import jmaster.io.project2.entity.Group;
import jmaster.io.project2.entity.User;
import jmaster.io.project2.repo.GroupRepo;
import jmaster.io.project2.repo.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {
    @Autowired
    GroupRepo groupRepo;
    @Autowired
    UserRepo userRepo;

    @Transactional
    public void create(GroupDTO groupDTO) {
        Group group = new Group();
        group.setName(groupDTO.getName());

        List<User> users = new ArrayList<>();

        for (UserDTO userDTO : groupDTO.getUsers()) {
            User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);
            users.add(user);
        }
        group.setUsers(users);

        groupRepo.save(group);
    }

    @Transactional
    public void update(GroupDTO groupDTO) {
        Group group = groupRepo.findById(groupDTO.getId()).orElseThrow(NoResultException::new);
        group.setName(groupDTO.getName());

        // neu co du lieu bang trung gian @JoinTable
        if (group.getUsers() != null) {
            group.getUsers().clear();

            for (UserDTO userDTO : groupDTO.getUsers()) {
                User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);
                group.getUsers().add(user);
            }
        } else { // them moi
            List<User> users = new ArrayList<>();

            for (UserDTO userDTO : groupDTO.getUsers()) {
                User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);
                users.add(user);
            }
            group.setUsers(users);
        }
        groupRepo.save(group);

    }

    // delete by userrole id
    @Transactional
    public void delete(int id) {
        groupRepo.deleteById(id);
    }

    // delete All
    @Transactional
    public void deleteAll(List<Integer> ids) {
        groupRepo.deleteAllById(ids);
    }

    // lay ra theo id
    public GroupDTO getById(int id) {
        Group group = groupRepo.findById(id).orElseThrow(NoResultException::new);
        return new ModelMapper().map(group, GroupDTO.class);
    }

    //
    public PageDTO<GroupDTO> search(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
// add if else ...
        Page<Group> pageRS = groupRepo.searchByName(name, pageable);

        PageDTO<GroupDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<GroupDTO> groupDTOs = new ArrayList<>();
        for (Group group : pageRS.getContent()) {
            groupDTOs.add(new ModelMapper().map(group, GroupDTO.class));
        }
        pageDTO.setContents(groupDTOs);

        return pageDTO;
    }
}
