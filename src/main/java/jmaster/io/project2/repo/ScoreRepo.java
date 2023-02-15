package jmaster.io.project2.repo;

import jmaster.io.project2.entity.Score;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScoreRepo extends JpaRepository<Score, Integer> {

    @Query("select s from Score s  where s.score = :x")
    Page<Score> searchByScore(@Param("x") double s, Pageable pageable);
}
