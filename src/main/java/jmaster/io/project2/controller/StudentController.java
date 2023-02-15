package jmaster.io.project2.controller;

import jmaster.io.project2.dto.PageDTO;
import jmaster.io.project2.dto.StudentDTO;
import jmaster.io.project2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping("/new")
    public String add() {
        return "user/add.html";
    }

    @PostMapping("new")
    public String add(@ModelAttribute StudentDTO studentDTO) {
        studentService.create(studentDTO);
        return "redirect:/student/search";
    }

    @GetMapping("/get/{id}") // get/1
    public String get(@PathVariable("id") int id, Model model) {
        model.addAttribute("student", studentService.getById(id));
        return "student/detail.html";
    }

    // delete
    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        studentService.delete(id);
        return "redirect:/student/search";
    }

    // edit
    @GetMapping("/edit") // ?id=1
    public String edit(@RequestParam("id") int id, Model model) {
        model.addAttribute("student", studentService.getById(id));
        return "student/edit.html";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute StudentDTO studentDTO)  {
        studentService.update(studentDTO);
        return "redirect:/student/search";
    }


    @GetMapping("/search") //String studentCode
    public String search(
            @RequestParam(name = "studentCode", required = false) String studentCode,

            @RequestParam(name = "start", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date start,
            @RequestParam(name = "end", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date end,

            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page,

            Model model
    ) {
        size = size == null ? 10 : size;
        page = page == null ? 0 : page;
        studentCode = studentCode == null ? "" : studentCode;

        PageDTO<StudentDTO> pageRS = studentService.searchByCode("%" + studentCode + "%", page, size);

        model.addAttribute("totalPage", pageRS.getTotalPages());
        model.addAttribute("count", pageRS.getTotalElements());
        model.addAttribute("studentList", pageRS.getContents());

        model.addAttribute("studentCode", studentCode);
        model.addAttribute("start", start);
        model.addAttribute("end", end);

        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "student/search.html";
    }
}
