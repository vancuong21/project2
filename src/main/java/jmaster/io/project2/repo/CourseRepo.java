package jmaster.io.project2.repo;

import jmaster.io.project2.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepo extends JpaRepository<Course, Integer> {
    @Query("SELECT c FROM Course c  where c.name like :x ")
    Page<Course> searchByName(@Param("x") String s, Pageable pageable);
}
