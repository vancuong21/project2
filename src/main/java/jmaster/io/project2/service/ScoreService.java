package jmaster.io.project2.service;

import jmaster.io.project2.dto.ScoreDTO;
import jmaster.io.project2.entity.Course;
import jmaster.io.project2.entity.Score;
import jmaster.io.project2.repo.CourseRepo;
import jmaster.io.project2.repo.ScoreRepo;
import jmaster.io.project2.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

@Service
public class ScoreService {
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    ScoreRepo scoreRepo;
    @Autowired
    CourseRepo courseRepo;

    @Transactional
    public void create(ScoreDTO scoreDTO) {
        Score score = new Score();
        //      Student student = studentRepo.findById(StudentDTO.);
        Course course = courseRepo.findById(scoreDTO.getCourse().getId()).orElseThrow(NoResultException::new);

        score.setScore(scoreDTO.getScore());
        //     score.setStudent(student);
        score.setCourse(course);

        scoreRepo.save(score);
    }
}
