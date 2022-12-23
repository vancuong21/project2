package jmaster.io.project2.service;

import jmaster.io.project2.dto.PageDTO;
import jmaster.io.project2.dto.UserDTO;
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
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    UserRoleRepo userRoleRepo;

    @Transactional
    public void create(UserDTO userDTO) {
        User user = new User();
        // convert dto -> entity
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setBirthdate(userDTO.getBirthdate());
        user.setPassword(userDTO.getPassword());
        user.setAvatar(userDTO.getAvatar());

        userRepo.save(user);

        // add user role
        List<UserRoleDTO> userRoleDTOs = userDTO.getUserRoles(); // lam thu cong, doc tung thang
        for (UserRoleDTO userRoleDTO : userRoleDTOs) {
            if (userRoleDTO.getRole() != null) {
                // save to db
                UserRole userRole = new UserRole();
                userRole.setUser(user);
                userRole.setRole(userRoleDTO.getRole());

                userRoleRepo.save(userRole);
            }
        }
    }

    // update
    @Transactional
    public void update(UserDTO userDTO) {
        User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);
        // convert dto -> entity
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setBirthdate(userDTO.getBirthdate());
        //    user.setPassword(userDTO.getPassword());
        user.setAvatar(userDTO.getAvatar());

        userRepo.save(user);
    }

    // update password rieng
    @Transactional
    public void updatePassword(UserDTO userDTO) {
        User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);
        // convert dto -> entity
        user.setPassword(userDTO.getPassword());

        userRepo.save(user);
    }

    // delete by user id
    @Transactional
    public void delete(int id) {
        userRepo.deleteById(id);
    }

    // delete All
    @Transactional
    public void deleteAll(List<Integer> ids) {
        userRepo.deleteAllById(ids);
    }

    public PageDTO<UserDTO> searchByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<User> pageRS = userRepo.searchByName("%" + name + "%", pageable);

        PageDTO<UserDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : pageRS.getContent()) {
            userDTOs.add(new ModelMapper().map(user, UserDTO.class));
        }
        pageDTO.setContents(userDTOs);

        return pageDTO;
    }

    // lay nguoc ra : repo lay id -> service : tra ra dto -> controller
    public UserDTO getById(int id) {
        User user = userRepo.findById(id).orElseThrow(NoResultException::new); // ko tim thay thi ban ra exception
        UserDTO userDTO = new UserDTO();

        userDTO.setName(user.getName());
        userDTO.setUsername(user.getUsername());
        userDTO.setBirthdate(user.getBirthdate());
        userDTO.setPassword(user.getPassword());
        userDTO.setAvatar(user.getAvatar());
        // doc ra thoai mai
        // co the them
        userDTO.setCreatedAt(user.getCreatedAt());

        // cach 2 : modelmapper
//        UserDTO userDTO1 = new ModelMapper().map(user, UserDTO.class);

        return userDTO;
    }
}
