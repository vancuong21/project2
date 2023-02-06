package jmaster.io.project2.service;

import jmaster.io.project2.dto.PageDTO;
import jmaster.io.project2.dto.StudentDTO;
import jmaster.io.project2.entity.Student;
import jmaster.io.project2.entity.User;
import jmaster.io.project2.entity.UserRole;
import jmaster.io.project2.repo.StudentRepo;
import jmaster.io.project2.repo.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    UserRepo userRepo;

    @Transactional
    @CacheEvict(cacheNames = "students", allEntries = true)
    public void create(StudentDTO studentDTO) {
        // them: check user if role = ROLE_STUDENT
        User user = userRepo.findById(studentDTO.getId()).orElseThrow(NoResultException::new);

        for (UserRole userRole : user.getUserRoles()) {
            if (userRole.getRole().equals("ROLE_STUDENT")) {
                Student student = new Student();
                student.setId(studentDTO.getId());
                student.setStudentCode(studentDTO.getStudentCode());

                studentRepo.save(student);
                return; // ket thuc
            }
        }
    }

    @Transactional
 //   @CacheEvict(cacheNames = "students", allEntries = true) // cach 1
    @Caching(evict = {
            @CacheEvict(cacheNames = "students", allEntries = true), // cach 2 : xoa cache nhieu
            @CacheEvict(cacheNames = "users", allEntries = true)
    })
    public void update(StudentDTO studentDTO) {
        Student student = studentRepo.findById(studentDTO.getId()).orElseThrow(NoResultException::new);

        student.setStudentCode(studentDTO.getStudentCode());

        studentRepo.save(student);
    }

    @Transactional
    public void delete(int id) {
        studentRepo.deleteById(id);
    }

    public StudentDTO findById(int id) {
        Student student = studentRepo.findById(id).orElseThrow(NoResultException::new);
        return new ModelMapper().map(student, StudentDTO.class);
    }

    // lay ra theo id
    public StudentDTO getById(int id) {
        Student student = studentRepo.findById(id).orElseThrow(NoResultException::new);
        return new ModelMapper().map(student, StudentDTO.class);
    }


    // nhom search
    @Cacheable(cacheNames = "students") // luu gia tri tra ve trong bo nho cache
    public PageDTO<StudentDTO> search(String name, String studentCode, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Student> pageRS = null;
        if (StringUtils.hasText(name) && StringUtils.hasText(studentCode))
            pageRS = studentRepo.searchByNameAndCode(name, studentCode, pageable);
        else if (StringUtils.hasText(name))
            pageRS = studentRepo.searchByName(name, pageable);
        else if (StringUtils.hasText(studentCode))
            pageRS = studentRepo.searchByCode(studentCode, pageable);
        else
            pageRS = studentRepo.findAll(pageable);

        PageDTO<StudentDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<StudentDTO> studentDTOs = new ArrayList<>();
        for (Student student : pageRS.getContent()) {
            studentDTOs.add(new ModelMapper().map(student, StudentDTO.class));
        }
        pageDTO.setContents(studentDTOs);

        return pageDTO;
    }


}
