package jmaster.io.project2.rest;

import jmaster.io.project2.dto.GroupDTO;
import jmaster.io.project2.dto.ResponseDTO;
import jmaster.io.project2.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/group")
public class RESTGroupController {
    @Autowired
    GroupService groupService;

    /**
     * @Valid : chỉ dùng với RequestBody, ModelAttribute
     * để check cái @NotBlank, ...@Size,..
     */
    @PostMapping("/")
    public ResponseDTO<GroupDTO> add(@RequestBody @Valid GroupDTO groupDTO) {
        groupService.create(groupDTO);
        return ResponseDTO.<GroupDTO>builder().status(200).data(groupDTO).build();
    }


}
