package jmaster.io.project2.controller;


import jmaster.io.project2.dto.PageDTO;
import jmaster.io.project2.dto.UserDTO;
import jmaster.io.project2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

@Controller
@RequestMapping("/user")
public class UserControler {
    @Autowired
    UserService userService;

    @GetMapping("/new")
    public String add() {
        return "user/add.html";
    }

    @PostMapping("new")
    public String add(@ModelAttribute UserDTO userDTO) throws IOException {
        if (userDTO.getFile() != null && !userDTO.getFile().isEmpty()) {
            final String UPLOAD_FOLDER = "C:\\Users\\cuong\\Downloads\\file\\";

            String filename = userDTO.getFile().getOriginalFilename();
            File newFile = new File(UPLOAD_FOLDER + filename);

            userDTO.getFile().transferTo(newFile);
            userDTO.setAvatar(filename);
        }
        // goi qua Service
        userService.create(userDTO);
        return "redirect:/user/search";
    }

    @GetMapping("/get/{id}") // get/1
    public String get(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "user/detail.html";
    }

    // delete
    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        userService.delete(id);
        return "redirect:/user/search";
    }

    // edit
    @GetMapping("/edit") // ?id=1
    public String edit(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "user/edit.html";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute UserDTO editUser) throws IllegalStateException, IOException {
        if (!editUser.getFile().isEmpty()) {
            final String UPLOAD_FOLDER = "D:/file/";

            String filename = editUser.getFile().getOriginalFilename();
            File newFile = new File(UPLOAD_FOLDER + filename);

            editUser.getFile().transferTo(newFile);

            editUser.setAvatar(filename);// save to db
        }

        // lay du lieu can update tu edit qua current, de tranh mat du lieu cu
        userService.update(editUser);
        return "redirect:/user/search";
    }


    // /user/download?filename=abc.jpg
    @GetMapping("/download")
    public void download(@RequestParam("filename") String filename,
                         HttpServletResponse response) throws IOException {
        final String UPLOAD_FOLDER = "C:\\Users\\cuong\\Downloads\\file\\";

        File file = new File(UPLOAD_FOLDER + filename);
        // copy file vaof response de dowload
        Files.copy(file.toPath(), response.getOutputStream());
    }

    @GetMapping("/search")
    public String search(
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "name", required = false) String name,

            @RequestParam(name = "start", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date start,
            @RequestParam(name = "end", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date end,

            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page,

            Model model
    ) {
        size = size == null ? 10 : size;
        page = page == null ? 0 : page;
        name = name == null ? "" : name;

        PageDTO<UserDTO> pageRS = userService.searchByName("%" + name + "%", page, size);

        model.addAttribute("totalPage", pageRS.getTotalPages());
        model.addAttribute("count", pageRS.getTotalElements());
        model.addAttribute("userList", pageRS.getContents());


        // luu lai du lieu set sang view lai
        model.addAttribute("id", id);
        model.addAttribute("name", name);
        model.addAttribute("start", start);
        model.addAttribute("end", end);

        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "user/search.html";
    }


}
