package ru.sd.MyBookShop.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class GenresController {
    private final Logger logger = Logger.getLogger(GenresController.class);
    @GetMapping("/genres")
    public String getGenresPage() {
        logger.info("getGenresPage");
        return "genres/index";
    }

    
}
