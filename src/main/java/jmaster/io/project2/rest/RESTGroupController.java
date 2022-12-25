package jmaster.io.project2.rest;

import jmaster.io.project2.dto.GroupDTO;
import jmaster.io.project2.dto.PageDTO;
import jmaster.io.project2.dto.ResponseDTO;
import jmaster.io.project2.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @PutMapping("/")
    public ResponseDTO<GroupDTO> update(@RequestBody GroupDTO groupDTO) {
        // goi qua Service
        groupService.update(groupDTO);
        return ResponseDTO.<GroupDTO>builder().status(200).data(groupDTO).build(); // cach 2
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<GroupDTO> delete(@PathVariable("id") int id) {
        groupService.delete(id);
        return ResponseDTO.<GroupDTO>builder().status(200).build(); // cach 2
    }

    @GetMapping("/{id}")
    public ResponseDTO<GroupDTO> get(@PathVariable("id") int id) {
        GroupDTO group = groupService.getById(id);
        return ResponseDTO.<GroupDTO>builder().status(200).data(group).build();
    }

    @PostMapping("/search")
    public ResponseDTO<PageDTO<GroupDTO>> search(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page

    ) {
        size = size == null ? 10 : size;
        page = page == null ? 0 : page;

        return ResponseDTO.<PageDTO<GroupDTO>>builder().status(200)
                .data(groupService.search(name, page, size))
                .build();
    }


}
