package ru.sd.MyBookShop.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sd.MyBookShop.services.BookService;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final Logger logger = Logger.getLogger(BooksController.class);

    private BookService bookService;

    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/slug.html")
    public String getBookInfo() {
        logger.info("Get book data info");
        return "books/slug";
    }

    @GetMapping("/author.html")
    public String getAllBooksOfAuthor() {
        return "books/author.html";
    }

//    @GetMapping("/recent")
//    public String getRecentBooks() {
//        logger.info("getRecentBooks");
//        return "books/recent";
//    }
//
//    @GetMapping("/popular")
//    public String getPopularBooks() {
//        logger.info("getPopularBooks");
//        return "books/popular";
//    }

}

