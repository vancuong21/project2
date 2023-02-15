package jmaster.io.project2.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Student {
    @Id
    private Integer id;
    @Column(unique = true)
    private String studentCode;
    @OneToOne
    @PrimaryKeyJoinColumn
    private User user;

}
