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
    @CreatedDate // tu gen
    @Column(updatable = false)
    private Date createdAt;
    @LastModifiedDate
    private Date lastUpdateAt;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserRole> userRoles;
    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Group> groups;
    private String email;
}
