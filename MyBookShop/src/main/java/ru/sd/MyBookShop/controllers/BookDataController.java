package ru.sd.MyBookShop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sd.MyBookShop.services.BookService;

import java.util.logging.Logger;

@Controller
public class BookDataController {

    private BookService bookService;

    @Autowired
    public BookDataController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("/books/slug.html")
    public String getBookInfo() {
        Logger.getLogger(BookDataController.class.getName()).info("Getting " +
                "book data info");
        return "books/slug";
    }
}

