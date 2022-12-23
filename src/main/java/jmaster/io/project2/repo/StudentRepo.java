package jmaster.io.project2.repo;

import jmaster.io.project2.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepo extends JpaRepository<Student, Integer> {
    // jpql : tham khao them
    @Query("select s from Student s join s.user u where u.name like :x")
    Page<Student> searchByName(@Param("x") String s, Pageable pageable);

    @Query("select s from Student s  where s.studentCode = :x")
    Page<Student> searchByCode(@Param("x") String s, Pageable pageable);

    @Query("select s from Student s join s.user u where s.studentCode like :code and u.name like :name")
    Page<Student> searchByNameAndCode(@Param("code") String code, @Param("name") String name, Pageable pageable);

}
