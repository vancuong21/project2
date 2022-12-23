package jmaster.io.project2.rest;

import jmaster.io.project2.dto.PageDTO;
import jmaster.io.project2.dto.ResponseDTO;
import jmaster.io.project2.dto.UserDTO;
import jmaster.io.project2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/api/user")
public class RESTUserController {
    @Autowired
    UserService userService;

    @PostMapping("new")
    public ResponseDTO<UserDTO> add(@ModelAttribute UserDTO userDTO) throws IOException {
        if (userDTO.getFile() != null && !userDTO.getFile().isEmpty()) {
            final String UPLOAD_FOLDER = "C:\\Users\\cuong\\Downloads\\file\\";

            String filename = userDTO.getFile().getOriginalFilename();
            File newFile = new File(UPLOAD_FOLDER + filename);

            userDTO.getFile().transferTo(newFile);
            userDTO.setAvatar(filename);
        }
        // goi qua Service
        userService.create(userDTO);
//        ResponseDTO<Void> responseDTO = new ResponseDTO<>(); // cach 1
//        responseDTO.setStatus(200);
//        return responseDTO;
        return ResponseDTO.<UserDTO>builder().status(200).data(userDTO).build(); // cach 2
    }

    @GetMapping("/get/{id}") // get/10
    public ResponseDTO<UserDTO> get(@PathVariable("id") int id) {
        UserDTO user = userService.getById(id);
        return ResponseDTO.<UserDTO>builder().status(200).data(user).build(); // cach 2
    }

    @GetMapping("/delete")
    public ResponseDTO<Void> delete(@RequestParam("id") int id) {
        userService.delete(id);
        return ResponseDTO.<Void>builder().status(200).build();
    }

    @PostMapping("/edit")
    public ResponseDTO<Void> edit(@ModelAttribute UserDTO editUser) throws IllegalStateException, IOException {
        if (!editUser.getFile().isEmpty()) {
            final String UPLOAD_FOLDER = "D:/file/";

            String filename = editUser.getFile().getOriginalFilename();
            File newFile = new File(UPLOAD_FOLDER + filename);

            editUser.getFile().transferTo(newFile);

            editUser.setAvatar(filename);// save to db
        }

        // lay du lieu can update tu edit qua current, de tranh mat du lieu cu
        userService.update(editUser);
        return ResponseDTO.<Void>builder().status(200).build();
    }

    @GetMapping("/search")
    public ResponseDTO<PageDTO<UserDTO>> search(
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "name", required = false) String name,

            @RequestParam(name = "start", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date start,
            @RequestParam(name = "end", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date end,

            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page

    ) {
        size = size == null ? 10 : size;
        page = page == null ? 0 : page;
        name = name == null ? "" : name;

        PageDTO<UserDTO> pageRS = userService.searchByName("%" + name + "%", page, size);

        return ResponseDTO.<PageDTO<UserDTO>>builder().status(200).data(pageRS).build();
    }
}
