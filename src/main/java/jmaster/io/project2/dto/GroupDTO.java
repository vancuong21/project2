package jmaster.io.project2.dto;

import lombok.Data;

import java.util.List;

@Data
public class GroupDTO {
    private Integer id;
    private String name;

    //    @JsonIgnore //loi o thang nay
    private List<UserDTO> users;
}
