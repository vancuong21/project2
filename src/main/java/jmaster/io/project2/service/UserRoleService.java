package jmaster.io.project2.service;

import jmaster.io.project2.dto.PageDTO;
import jmaster.io.project2.dto.UserRoleDTO;
import jmaster.io.project2.entity.User;
import jmaster.io.project2.entity.UserRole;
import jmaster.io.project2.repo.UserRepo;
import jmaster.io.project2.repo.UserRoleRepo;
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
public class UserRoleService {
    @Autowired
    UserRoleRepo userRoleRepo;
    @Autowired
    UserRepo userRepo;

    @Transactional
    public void create(UserRoleDTO userRoleDTO) {
        UserRole userRole = new UserRole();
        userRole.setRole(userRoleDTO.getRole());

        // tim user id trong db user de set user role
        User user = userRepo.findById(userRoleDTO.getUserId()).orElseThrow(NoResultException::new);
        userRole.setUser(user);

        userRoleRepo.save(userRole);
    }

    @Transactional
    public void update(UserRoleDTO userRoleDTO) {
        UserRole userRole = userRoleRepo.findById(userRoleDTO.getId()).orElseThrow(NoResultException::new);
        userRole.setRole(userRoleDTO.getRole());

        User user = userRepo.findById(userRoleDTO.getUserId()).orElseThrow(NoResultException::new);
        userRole.setUser(user);

        userRoleRepo.save(userRole);
    }

    // delete by userrole id
    @Transactional
    public void delete(int id) {
        userRoleRepo.deleteById(id);
    }

    // delete by userId
    @Transactional
    public void deleteByUserId(int userId) {
        userRoleRepo.deleteByUserId(userId);
    }

    // delete All
    @Transactional
    public void deleteAll(List<Integer> ids) {
        userRoleRepo.deleteAllById(ids);
    }

    // lay ra theo id
    public UserRoleDTO getById(int id) {
        UserRole userRole = userRoleRepo.findById(id).orElseThrow(NoResultException::new);
        return new ModelMapper().map(userRole, UserRoleDTO.class);
    }

    //
    public PageDTO<UserRoleDTO> searchByUserId(int userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<UserRole> pageRS = userRoleRepo.searchByUserId(userId, pageable);

        PageDTO<UserRoleDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<UserRoleDTO> userRoleDTOs = new ArrayList<>();
        for (UserRole userRole : pageRS.getContent()) {
            userRoleDTOs.add(new ModelMapper().map(userRole, UserRoleDTO.class));
        }
        pageDTO.setContents(userRoleDTOs);

        return pageDTO;
    }

    public PageDTO<UserRoleDTO> searchByRole(String role, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<UserRole> pageRS = userRoleRepo.searchByRole(role, pageable);

        PageDTO<UserRoleDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<UserRoleDTO> userRoleDTOs = new ArrayList<>();
        for (UserRole userRole : pageRS.getContent()) {
            userRoleDTOs.add(new ModelMapper().map(userRole, UserRoleDTO.class));
        }
        pageDTO.setContents(userRoleDTOs);

        return pageDTO;
    }
}
