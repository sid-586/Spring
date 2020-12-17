package ru.sd.MyBookShop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.logging.Logger;

@Controller
public class GenresController {

    @GetMapping("/genres")
    public String getGenresPage() {
        Logger.getLogger(GenresController.class.getName()).info(
                "getGenresPage");
        return "genres/index";
    }
}
