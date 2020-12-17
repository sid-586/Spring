package ru.sd.MyBookShop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.logging.Logger;

@Controller
public class ErrorController {

    @GetMapping("/404")
    public String error404() {
        Logger.getLogger(ErrorController.class.getName()).info("GetMapping errors 404");
        return "errors/404";
    }
}
