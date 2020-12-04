package ru.sd.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sd.app.exceptions.BookShelfLoginException;

@Controller
@ControllerAdvice
public class ErrorsController {
    private final Logger logger = Logger.getLogger(ErrorsController.class);

    @GetMapping("/404")
    public String error404() {
        logger.info("GetMapping errors 404");
        return "errors/404";
    }

    @ExceptionHandler(BookShelfLoginException.class)
    public String handlerEmptyLoginFieldsError(Model model,
                                               BookShelfLoginException exception) {
        logger.info("No such username - ErrorsController");
        model.addAttribute("error_message", exception.getMessage());
        return "errors/404";
    }

}
