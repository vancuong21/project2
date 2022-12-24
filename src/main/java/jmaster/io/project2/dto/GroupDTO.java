package jmaster.io.project2.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class GroupDTO {
    private Integer id;
    @NotBlank
    @Size(min = 6)
    private String name;

    //    @JsonIgnore //loi o thang nay
    private List<UserDTO> users;
}
