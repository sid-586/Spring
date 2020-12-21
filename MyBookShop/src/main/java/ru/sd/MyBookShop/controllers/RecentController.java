package ru.sd.MyBookShop.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class RecentController {
    private final Logger logger =
            Logger.getLogger(RecentController.class);
    @GetMapping("/books/recent")
    public String getRecentBooks() {
        logger.info("getRecentBooks");
        return "books/recent";
    }
}
