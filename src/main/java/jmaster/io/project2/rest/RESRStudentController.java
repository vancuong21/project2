package jmaster.io.project2.rest;

import jmaster.io.project2.entity.Student;
import jmaster.io.project2.repo.StudentRepo;
import jmaster.io.project2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class RESRStudentController {
    @Autowired
    StudentService studentService;

    @Autowired
    StudentRepo studentRepo;

    @GetMapping()
    public List<Student> getAll() {

        return studentRepo.findAll();
    }
//
//    @PostMapping("/")
//    public ResponseDTO<StudentDTO> add(@RequestBody StudentDTO studentDTO){
//        // goi qua Service
//        studentService.create(studentDTO);
////        ResponseDTO<Void> responseDTO = new ResponseDTO<>(); // cach 1
////        responseDTO.setStatus(200);
////        return responseDTO;
//        return ResponseDTO.<StudentDTO>builder().status(200).data(studentDTO).build(); // cach 2
//    }
//    @PutMapping("/")
//    public ResponseDTO<StudentDTO> update(@RequestBody StudentDTO studentDTO){
//        // goi qua Service
//        studentService.update(studentDTO);
//        return ResponseDTO.<StudentDTO>builder().status(200).data(studentDTO).build(); // cach 2
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseDTO<StudentDTO> delete(@PathVariable("id") int id) {
//        studentService.delete(id);
//        return ResponseDTO.<StudentDTO>builder().status(200).build(); // cach 2
//    }
//
//    @GetMapping("/{id}")
//    public ResponseDTO<StudentDTO> get(@PathVariable("id") int id) {
//        StudentDTO student = studentService.findById(id);
//        return ResponseDTO.<StudentDTO>builder().status(200).data(student).build();
//    }
//
//    @PostMapping("/search")
//    public ResponseDTO<PageDTO<StudentDTO>> search(
//            @RequestParam(name = "name", required = false) String name,
//            @RequestParam(name = "studentCode", required = false) String studentCode,
//            @RequestParam(name = "size", required = false) Integer size,
//            @RequestParam(name = "page", required = false) Integer page
//
//    ) {
//        size = size == null ? 10 : size;
//        page = page == null ? 0 : page;
//
//        return ResponseDTO.<PageDTO<StudentDTO>>builder().status(200)
//                .data(studentService.search(name, studentCode, page, size))
//                .build();
//    }

}
