package jmaster.io.project2.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private double score; // diem thi mon hoc/ nguoi
    @ManyToOne
    private Student student;
    @ManyToOne
    private Course course;
}
