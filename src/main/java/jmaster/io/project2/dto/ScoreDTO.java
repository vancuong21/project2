package jmaster.io.project2.dto;

import lombok.Data;

@Data
public class ScoreDTO {

    private Integer id;
    private double score; // diem thi mon hoc/ nguoi

    private int studentId;

    private CourseDTO course;
}
