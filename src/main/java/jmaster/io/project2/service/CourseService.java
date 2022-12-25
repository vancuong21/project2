package jmaster.io.project2.service;

import jmaster.io.project2.dto.CourseDTO;
import jmaster.io.project2.dto.PageDTO;
import jmaster.io.project2.entity.Course;
import jmaster.io.project2.repo.CourseRepo;
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
public class CourseService {
    @Autowired
    CourseRepo courseRepo;

    @Transactional
    public void create(CourseDTO courseDTO) {
        Course course = new Course();
        course.setName(courseDTO.getName());

        courseRepo.save(course);
    }

    @Transactional
    public void update(CourseDTO courseDTO) {
        Course course = courseRepo.findById(courseDTO.getId()).orElseThrow(NoResultException::new);
        course.setName(courseDTO.getName());

        courseRepo.save(course);
    }

    // delete by userrole id
    @Transactional
    public void delete(int id) {
        courseRepo.deleteById(id);
    }

    // delete All
    @Transactional
    public void deleteAll(List<Integer> ids) {
        courseRepo.deleteAllById(ids);
    }

    // lay ra theo id
    public CourseDTO getById(int id) {
        Course course = courseRepo.findById(id).orElseThrow(NoResultException::new);
        return new ModelMapper().map(course, CourseDTO.class);
    }

    //
    public PageDTO<CourseDTO> search(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Course> pageRS = courseRepo.searchByName(name, pageable);

        PageDTO<CourseDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<CourseDTO> courseDTOs = new ArrayList<>();
        for (Course course : pageRS.getContent()) {
            courseDTOs.add(new ModelMapper().map(course, CourseDTO.class));
        }
        pageDTO.setContents(courseDTOs);

        return pageDTO;
    }

}
