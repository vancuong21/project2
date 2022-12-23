package jmaster.io.project2.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Student {
    // 1 student - 1 user, nên dùng luôn khoá chính User làm khoá chính id (Student)
    @Id
    private Integer id;
    @Column(unique = true)
    private String studentCode;
    @OneToOne
    @PrimaryKeyJoinColumn // ràng buộc student_id va user ko dc trùng nhau
    private User user;

    // có thể thêm @OneToMany List<Scrore> nếu cần
}
