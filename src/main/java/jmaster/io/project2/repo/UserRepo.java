package jmaster.io.project2.repo;

import jmaster.io.project2.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface UserRepo extends JpaRepository<User, Integer> {
    @Query("select u from User u where month(u.birthdate) = :m and day(u.birthdate) = :d ")
    List<User> findByBirthdate(@Param("d") int d, @Param("m") int m);


    // tat ca Phan Trang

    // tim theo Name
    @Query("SELECT u FROM User u WHERE u.name LIKE :x ")
    Page<User> searchByName(@Param("x") String s, Pageable pageable);

    // tim theo ngay tao lon hon, nho hon
    @Query("SELECT u FROM User u " + "WHERE u.createdAt >= :start and u.createdAt <= :end")
    Page<User> searchByDate(@Param("start") Date start, @Param("end") Date end, Pageable pageable);

    // tim theo ngay tao lon hon
    @Query("SELECT u FROM User u " + "WHERE u.createdAt >= :start")
    Page<User> searchByStartDate(@Param("start") Date start, Pageable pageable);

    // tim theo ngay tao nho hon
    @Query("SELECT u FROM User u " + "WHERE u.createdAt <= :end")
    Page<User> searchByEndDate(@Param("end") Date end, Pageable pageable);

    // tim theo ten va ngay tao
    @Query("SELECT u FROM User u WHERE u.name LIKE :x AND u.createdAt >= :start AND u.createdAt <= :end")
    Page<User> searchByNameAndDate(@Param("x") String s, @Param("start") Date start, @Param("end") Date end,
                                   Pageable pageable);

    // tim theo ten va ngay tao
    @Query("SELECT u FROM User u WHERE u.name LIKE :x AND u.createdAt >= :start")
    Page<User> searchByNameAndStartDate(@Param("x") String s, @Param("start") Date start, Pageable pageable);

    // tim theo ten va ngay tao
    @Query("SELECT u FROM User u WHERE u.name LIKE :x AND u.createdAt <= :end")
    Page<User> searchByNameAndEndDate(@Param("x") String s, @Param("end") Date end, Pageable pageable);
}
