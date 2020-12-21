package ru.sd.MyBookShop.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ErrorController {
    private final Logger logger = Logger.getLogger(ErrorController.class);
    @GetMapping("/404")
    public String error404() {
        logger.info("GetMapping errors 404");
        return "errors/404";
    }
}
