package jmaster.io.project2.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne // many UserRole - one User
    //@JoinColumn("user_id") // mac dinh tu gen
    private User user;
    private String role; // ADMIN, MEMBER
}
