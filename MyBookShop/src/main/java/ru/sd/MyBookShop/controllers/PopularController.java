package ru.sd.MyBookShop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
@RequestMapping("/popular")
public class PopularController {

    @GetMapping
    public String getPopularBooks() {
        Logger.getLogger(PopularController.class.getName()).info("getPopularBooks");
        return "books/popular";
    }
}
