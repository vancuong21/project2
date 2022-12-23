package jmaster.io.project2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonFormat(pattern = "dd/MM/yyyy") // dung cho JSON
    private Date birthdate;
    @JsonIgnore // de bo qua , khi dung REST
    private MultipartFile file;
    private Date createdAt;

    @JsonManagedReference // bên này vẫn lấy full, còn bên UserRoleDTO...
    private List<UserRoleDTO> userRoles;
}
