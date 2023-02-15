package jmaster.io.project2.controller;

import jmaster.io.project2.dto.CourseDTO;
import jmaster.io.project2.dto.GroupDTO;
import jmaster.io.project2.dto.PageDTO;
import jmaster.io.project2.service.CourseService;
import jmaster.io.project2.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    CourseService courseService;

    @GetMapping("/new")
    public String add() {
        return "course/add.html";
    }

    @PostMapping("new")
    public String add(@ModelAttribute CourseDTO courseDTO) {
        courseService.create(courseDTO);
        return "redirect:/course/search";
    }

    @GetMapping("/get/{id}") // get/1
    public String get(@PathVariable("id") int id, Model model) {
        model.addAttribute("course", courseService.getById(id));
        return "course/detail.html";
    }

    // delete
    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        courseService.delete(id);
        return "redirect:/course/search";
    }

    // edit
    @GetMapping("/edit") // ?id=1
    public String edit(@RequestParam("id") int id, Model model) {
        model.addAttribute("course", courseService.getById(id));
        return "course/edit.html";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute CourseDTO courseDTO)  {
        courseService.update(courseDTO);
        return "redirect:/course/search";
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

        PageDTO<CourseDTO> pageRS = courseService.search("%" + name + "%", page, size);

        model.addAttribute("totalPage", pageRS.getTotalPages());
        model.addAttribute("count", pageRS.getTotalElements());
        model.addAttribute("courseList", pageRS.getContents());

        model.addAttribute("name", name);
        model.addAttribute("start", start);
        model.addAttribute("end", end);

        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "course/search.html";
    }
}
