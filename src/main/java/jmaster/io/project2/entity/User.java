package jmaster.io.project2.entity;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    private String name;
    @Column(name = "avatar")
    private String avatar; // luu url
    @Column(unique = true)
    private String username;
    private String password;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;

    //    @Transient // bo qua file , ko luu file vao db
//    private MultipartFile file;
    @CreatedDate // tu gen
    @Column(updatable = false)
    private Date createdAt;
    @LastModifiedDate
    private Date lastUpdateAt;

    // khong bat buoc,  1 user cos 1 ds userrole
    // dùng khi lấy danh sách userRole như 1 thuộc tính, thì sẽ tạo thêm
    // mappedBy : ten thuoc tinh ban UserRole
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserRole> userRoles;

    // tuong tu, dùng khi cần thiết
    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Group> groups;

    // cách tắt để join bảng trung gian, chỉ dùng khi có 2 thuộc tính : vd user_id và role
//    @ElementCollection
//    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
//    @Column(name = "role")
//    private List<String> roles;

    private String email;
}
