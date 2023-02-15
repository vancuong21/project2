package jmaster.io.project2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
public class UserDTO {
    private Integer id;
    @NotBlank // kiểm tra dữ liệu nhập vào
    private String name;
    private String avatar;// url
    private String username;
    private String password;
    @DateTimeFormat(pattern = "dd/MM/yyyy") // dung cho form-data
    private Date birthdate;

    private MultipartFile file;
    private Date createdAt;
    private List<UserRoleDTO> userRoles;
}
