package jmaster.io.project2.repo;

import jmaster.io.project2.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupRepo extends JpaRepository<Group, Integer> {
    @Query("SELECT g FROM Group g join g.users u where u.name like :x ")
    Page<Group> searchByName(@Param("x") String s, Pageable pageable);

    @Query("select g from Group  g join g.users u where u.id = :uid")
    Page<Group> searchByUserId(@Param("uid") int userId, Pageable pageable);

    @Query("SELECT g FROM Group g join g.users u where u.name like :x ")
    Page<Group> searchByUsersName(@Param("x") String s, Pageable pageable);
}
