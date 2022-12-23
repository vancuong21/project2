package jmaster.io.project2.repo;

import jmaster.io.project2.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepo extends JpaRepository<Score, Integer> {

}
