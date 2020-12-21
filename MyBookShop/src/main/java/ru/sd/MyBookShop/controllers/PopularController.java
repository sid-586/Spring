package ru.sd.MyBookShop.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/popular")
public class PopularController {
    private final Logger logger = Logger.getLogger(PopularController.class);

    @GetMapping
    public String getPopularBooks() {
        logger.info("getPopularBooks");
        return "books/popular";
    }
}
