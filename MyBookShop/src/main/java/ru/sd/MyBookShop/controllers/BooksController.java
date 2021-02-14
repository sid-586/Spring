package ru.sd.MyBookShop.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.sd.MyBookShop.data.BookPage;
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

    @GetMapping("/recommended")
    @ResponseBody
    public BookPage getNextPageOfRecommendedBooks
            (@RequestParam(name = "offset") Integer offset,
             @RequestParam(name = "limit") Integer limit) {
        logger.info("Get next page of recommended books");
        return
                new BookPage(bookService.getPageOfRecommendedBooks(offset, limit).getContent());
    }

    @ApiOperation("getting author from this book")
    @GetMapping("/author.html")
    public String getAllBooksOfAuthor() {
        return "books/author.html";
    }
}
