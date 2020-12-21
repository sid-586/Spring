package ru.sd.MyBookShop.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sd.MyBookShop.services.BookService;

@Controller
public class BookDataController {
    private final Logger logger = Logger.getLogger(BookDataController.class);

    private BookService bookService;

    public BookDataController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("/books/slug.html")
    public String getBookInfo() {
        logger.info("Getting book data info");
        return "books/slug";
    }
}

