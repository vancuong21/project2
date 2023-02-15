package jmaster.io.project2.controller;

import jmaster.io.project2.dto.PageDTO;
import jmaster.io.project2.dto.ScoreDTO;
import jmaster.io.project2.dto.StudentDTO;
import jmaster.io.project2.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/score")
public class ScoreController {
    @Autowired
    ScoreService scoreService;

    @GetMapping("/new")
    public String add() {
        return "score/add.html";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute ScoreDTO scoreDTO) {
        // goi qua Service
        scoreService.create(scoreDTO);
        return "redirect:/score/search";
    }

    // edit
    @GetMapping("/edit") // ?id=1
    public String edit(@RequestParam("id") int id, Model model) {
        model.addAttribute("score", scoreService.getById(id));
        return "score/edit.html";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute ScoreDTO scoreDTO) {
        scoreService.update(scoreDTO);
        return "redirect:/score/search";
    }

    @GetMapping("/get/{id}") // get/1
    public String get(@PathVariable("id") int id, Model model) {
        model.addAttribute("score", scoreService.getById(id));
        return "score/detail.html";
    }

    // delete
    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        scoreService.delete(id);
        return "redirect:/score/search";
    }
    @GetMapping("/search") //String studentCode
    public String search(
            @RequestParam(name = "score", required = false) Double score,

            @RequestParam(name = "start", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date start,
            @RequestParam(name = "end", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date end,

            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page,

            Model model
    ) {
        size = size == null ? 10 : size;
        page = page == null ? 0 : page;
        score = score == null ? 0 : score;

        PageDTO<ScoreDTO> pageRS = scoreService.searchByScore(score, page, size);

        model.addAttribute("totalPage", pageRS.getTotalPages());
        model.addAttribute("count", pageRS.getTotalElements());
        model.addAttribute("scoreList", pageRS.getContents());

        model.addAttribute("score", score);
        model.addAttribute("start", start);
        model.addAttribute("end", end);

        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "user/search.html";
    }
}
