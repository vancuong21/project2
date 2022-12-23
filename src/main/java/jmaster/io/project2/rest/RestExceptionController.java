package jmaster.io.project2.rest;

import jmaster.io.project2.dto.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.NoResultException;
import java.sql.SQLException;

@RestControllerAdvice(basePackages = "jmaster.io.project2.rest")
public class RestExceptionController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({NoResultException.class})
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseDTO<Void> noResult(NoResultException ex) {
        logger.info("sql ex: ", ex);
        return ResponseDTO.<Void>builder().status(404).error("not found").build();
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDTO<Void> exception(SQLException ex) {
        logger.error("sql ex: ", ex);
        return ResponseDTO.<Void>builder().status(500).error("sarver error").build();
    }
}
