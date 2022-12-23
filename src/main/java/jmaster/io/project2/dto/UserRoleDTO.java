package jmaster.io.project2.dto;

import lombok.Data;

@Data
public class UserRoleDTO {
    private Integer id;
    private Integer userId;
    private String userName;
    private String role; // ADMIN, MEMBER

//    @JsonIgnoreProperties("userRoles")// Cach1: lấy UserDTO bỏ qua thuộc tính userRoles : tránh vòng lặp vô hạn
//    @JsonBackReference // cach 2
//    private UserDTO user;
}
