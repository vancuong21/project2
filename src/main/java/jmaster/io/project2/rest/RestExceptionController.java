package jmaster.io.project2.rest;

import jmaster.io.project2.dto.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.NoResultException;
import java.util.List;

@RestControllerAdvice(basePackages = "jmaster.io.project2.rest")
public class RestExceptionController {
    // logger để lưu lại, ko phải system out
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({NoResultException.class})
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseDTO<Void> noResult(NoResultException ex) {
        logger.info("sql ex: ", ex);
        return ResponseDTO.<Void>builder().status(404).error("not found").build();
    }

    // lỗi phia người dùng, nhập ko đủ kí tự "name"
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseDTO<Void> noResult(MethodArgumentNotValidException ex) {
        logger.info("sql ex: ", ex);
        // cach 1: java 8
//        String message = ex.getBindingResult().getAllErrors().stream().map((error) -> {
//            return ((FieldError) error).getField() + " " + error.getDefaultMessage();
//        }).findAny().orElse("");

        // cach 2 : vong lap
//        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
//        String msg = "";
//        for (ObjectError e : errors) {
//            msg += e.getDefaultMessage();
//        }
        // nâng cao
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        String msg = "";
        for (ObjectError e : errors) {
            FieldError fieldError = (FieldError) e;
            msg += fieldError.getDefaultMessage() + " : " + e.getDefaultMessage() + ";";
        }

        return ResponseDTO.<Void>builder().status(404).error(msg).build();
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDTO<Void> exception(Exception ex) {
        logger.error("sql ex: ", ex);
        return ResponseDTO.<Void>builder().status(500).error("server error").build();
    }


}
