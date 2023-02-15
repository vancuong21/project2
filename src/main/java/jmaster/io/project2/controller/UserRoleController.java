package jmaster.io.project2.controller;

import jmaster.io.project2.dto.PageDTO;
import jmaster.io.project2.dto.UserRoleDTO;
import jmaster.io.project2.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user-role")
public class UserRoleController {
    @Autowired
    UserRoleService userRoleService;

    @GetMapping("/new")
    public String add() {
        return "userrole/add.html";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute UserRoleDTO userRoleDTO) {
        // goi qua Service
        userRoleService.create(userRoleDTO);
        return "redirect:/user-role/search";
    }

    // edit
    @GetMapping("/edit") // ?id=1
    public String edit(@RequestParam("id") int id, Model model) {
        model.addAttribute("userRole", userRoleService.getById(id));
        return "userrole/edit.html";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute UserRoleDTO userRoleDTO) {
        userRoleService.update(userRoleDTO);
        return "redirect:/user-role/search";
    }

    @GetMapping("/get/{id}") // get/1
    public String get(@PathVariable("id") int id, Model model) {
        model.addAttribute("userRole", userRoleService.getById(id));
        return "userrole/detail.html";
    }

    // delete
    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        userRoleService.delete(id);
        return "redirect:/user-role/search";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam(name = "role", required = false) String role,
            @RequestParam(name = "userId", required = false) Integer userId,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page,
            Model model
    ) {
        size = size == null ? 10 : size;
        page = page == null ? 0 : page;
        role = role == null ? "%%" : role; // like ket hop %% : search tat ca

        PageDTO<UserRoleDTO> pageRS = null;

        if (userId != null)
            pageRS = userRoleService.searchByUserId(userId, page, size);
        else
            pageRS = userRoleService.searchByRole(role, page, size);


        model.addAttribute("totalPage", pageRS.getTotalPages());
        model.addAttribute("count", pageRS.getTotalElements());
        model.addAttribute("userRoleList", pageRS.getContents());


        // luu lai du lieu set sang view lai
        model.addAttribute("userId", userId);
        model.addAttribute("role", role);
        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "userrole/search.html";
    }
}
