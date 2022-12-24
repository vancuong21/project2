package jmaster.io.project2.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.NoResultException;
import java.sql.SQLException;

@ControllerAdvice(basePackages = "jmaster.io.project2.controller")
public class ExceptionController {
    // log level
    Logger logger = LoggerFactory.getLogger(this.getClass());

    // bat cac loai exception tai day
    @ExceptionHandler({NoResultException.class})
    public String noResult(NoResultException ex) {
        logger.info("sql ex: ", ex);
        return "404.html";// view
    }

    @ExceptionHandler({Exception.class})
    public String exception(SQLException ex) {
        logger.error("sql ex: ", ex);
        return "exception.html";// view
    }
}
