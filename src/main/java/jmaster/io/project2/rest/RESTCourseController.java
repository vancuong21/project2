package jmaster.io.project2.rest;

import jmaster.io.project2.dto.CourseDTO;
import jmaster.io.project2.dto.PageDTO;
import jmaster.io.project2.dto.ResponseDTO;
import jmaster.io.project2.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course")
public class RESTCourseController {
    @Autowired
    CourseService courseService;

    @PostMapping("/")
    public ResponseDTO<CourseDTO> add(@ModelAttribute CourseDTO courseDTO) {

        courseService.create(courseDTO);

        return ResponseDTO.<CourseDTO>builder().status(200).data(courseDTO).build(); // cach 2
    }

    @PutMapping("/")
    public ResponseDTO<CourseDTO> update(@RequestBody CourseDTO courseDTO) {
        // goi qua Service
        courseService.update(courseDTO);
        return ResponseDTO.<CourseDTO>builder().status(200).data(courseDTO).build(); // cach 2
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<CourseDTO> delete(@PathVariable("id") int id) {
        courseService.delete(id);
        return ResponseDTO.<CourseDTO>builder().status(200).build(); // cach 2
    }

    @GetMapping("/{id}")
    public ResponseDTO<CourseDTO> get(@PathVariable("id") int id) {
        CourseDTO course = courseService.getById(id);
        return ResponseDTO.<CourseDTO>builder().status(200).data(course).build();
    }

    @PostMapping("/search")
    public ResponseDTO<PageDTO<CourseDTO>> search(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page

    ) {
        size = size == null ? 10 : size;
        page = page == null ? 0 : page;

        return ResponseDTO.<PageDTO<CourseDTO>>builder().status(200)
                .data(courseService.search(name, page, size))
                .build();
    }

}
