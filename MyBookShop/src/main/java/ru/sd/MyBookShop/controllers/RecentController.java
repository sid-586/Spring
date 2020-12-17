package ru.sd.MyBookShop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.logging.Logger;

@Controller
public class RecentController {

    @GetMapping("/books/recent")
    public String getRecentBooks() {
        Logger.getLogger(RecentController.class.getName()).info("getRecentBooks");
        return "books/recent";
    }
}
