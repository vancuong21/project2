package jmaster.io.project2.repo;

import jmaster.io.project2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupRepo extends JpaRepository<User, Integer> {
}
