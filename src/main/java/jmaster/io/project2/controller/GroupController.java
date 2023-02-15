package jmaster.io.project2.controller;

import jmaster.io.project2.dto.GroupDTO;
import jmaster.io.project2.dto.PageDTO;
import jmaster.io.project2.dto.StudentDTO;
import jmaster.io.project2.service.GroupService;
import jmaster.io.project2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/group")
public class GroupController {
    @Autowired
    GroupService groupService;

    @GetMapping("/new")
    public String add() {
        return "group/add.html";
    }

    @PostMapping("new")
    public String add(@ModelAttribute GroupDTO groupDTO) {
        groupService.create(groupDTO);
        return "redirect:/group/search";
    }

    @GetMapping("/get/{id}") // get/1
    public String get(@PathVariable("id") int id, Model model) {
        model.addAttribute("group", groupService.getById(id));
        return "group/detail.html";
    }

    // delete
    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        groupService.delete(id);
        return "redirect:/group/search";
    }

    // edit
    @GetMapping("/edit") // ?id=1
    public String edit(@RequestParam("id") int id, Model model) {
        model.addAttribute("group", groupService.getById(id));
        return "group/edit.html";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute GroupDTO groupDTO)  {
        groupService.update(groupDTO);
        return "redirect:/group/search";
    }


    @GetMapping("/search") //String studentCode
    public String search(
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

        PageDTO<GroupDTO> pageRS = groupService.search("%" + name + "%", page, size);

        model.addAttribute("totalPage", pageRS.getTotalPages());
        model.addAttribute("count", pageRS.getTotalElements());
        model.addAttribute("scoreList", pageRS.getContents());

        model.addAttribute("name", name);
        model.addAttribute("start", start);
        model.addAttribute("end", end);

        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "group/search.html";
    }
}
