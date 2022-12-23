package jmaster.io.project2.rest;

import jmaster.io.project2.dto.PageDTO;
import jmaster.io.project2.dto.UserRoleDTO;
import jmaster.io.project2.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-role")
public class UserRoleRESTController {
    @Autowired
    UserRoleService userRoleService;

    @PostMapping("/new")
    public void add(@ModelAttribute UserRoleDTO userRoleDTO) {
        userRoleService.create(userRoleDTO);
    }

    @PostMapping("/new-json")
    public void create(@RequestBody UserRoleDTO userRoleDTO) {
        userRoleService.create(userRoleDTO);
    }

    @DeleteMapping("/delete") // ?id=1
    public void delete(@RequestParam("id") int id) {
        userRoleService.delete(id);
    }

    @GetMapping("/get/{id}") // get/10
    public UserRoleDTO get(@PathVariable("id") int id) {
        return userRoleService.getById(id);
    }

    @GetMapping("/search")
    public PageDTO<UserRoleDTO> search(
            @RequestParam(name = "role", required = false) String role,
            @RequestParam(name = "userId", required = false) Integer userId,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page
    ) {
        size = size == null ? 10 : size;
        page = page == null ? 0 : page;
        role = role == null ? "%%" : role; // like ket hop %% : search tat ca

        PageDTO<UserRoleDTO> pageRS = null;

        if (userId != null)
            pageRS = userRoleService.searchByUserId(userId, page, size);
        else
            pageRS = userRoleService.searchByRole(role, page, size);

        return pageRS;
    }
}
