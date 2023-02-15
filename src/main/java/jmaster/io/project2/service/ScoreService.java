package jmaster.io.project2.service;

import jmaster.io.project2.dto.PageDTO;
import jmaster.io.project2.dto.ScoreDTO;
import jmaster.io.project2.dto.StudentDTO;
import jmaster.io.project2.entity.Course;
import jmaster.io.project2.entity.Score;
import jmaster.io.project2.entity.Student;
import jmaster.io.project2.repo.CourseRepo;
import jmaster.io.project2.repo.ScoreRepo;
import jmaster.io.project2.repo.StudentRepo;
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

        Student student = studentRepo.findById(scoreDTO.getStudent().getId()).orElseThrow(NoResultException::new);
        Course course = courseRepo.findById(scoreDTO.getCourse().getId()).orElseThrow(NoResultException::new);

        score.setScore(scoreDTO.getScore());
        score.setStudent(student);
        score.setCourse(course);

        scoreRepo.save(score);
    }

    @Transactional
    public void update(ScoreDTO scoreDTO) {
        Score score = scoreRepo.findById(scoreDTO.getId()).orElseThrow(NoResultException::new);
        score.setScore(scoreDTO.getScore());
        scoreRepo.save(score);
    }

    @Transactional
    public void delete(int id) {
        scoreRepo.deleteById(id);
    }

    // lay ra theo id
    public ScoreDTO getById(int id) {
        Score score = scoreRepo.findById(id).orElseThrow(NoResultException::new);
        return new ModelMapper().map(score, ScoreDTO.class);
    }

    public PageDTO<ScoreDTO> searchByScore(double score, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Score> pageRS = scoreRepo.searchByScore( + score  , pageable);

        PageDTO<ScoreDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<ScoreDTO> scoreDTOs = new ArrayList<>();
        for (Score score1 : pageRS.getContent()) {
            scoreDTOs.add(new ModelMapper().map(score, ScoreDTO.class));
        }
        pageDTO.setContents(scoreDTOs);

        return  pageDTO;
    }

}
