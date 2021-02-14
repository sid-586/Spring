package ru.sd.MyBookShop.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.sd.MyBookShop.data.BookPage;
import ru.sd.MyBookShop.services.BookService;

@Controller
@RequestMapping("/books/popular")
public class PopularController {
    private final Logger logger = Logger.getLogger(PopularController.class);
    private BookService bookService;

    public PopularController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String getPopularBooks(Model model) {
        logger.info("getPopularBooks");
        model.addAttribute("popularBooks",
                bookService.getPageOfPopularBooks(0, 20).getContent());
        return "books/popular";
    }

    @GetMapping("/next_page")
    @ResponseBody
    public BookPage getNextPageOfPopularBooks
            (@RequestParam(name = "offset") Integer offset,
             @RequestParam(name = "limit") Integer limit) {
        logger.info("Get next page of popular books");
        return new BookPage(bookService.getPageOfPopularBooks(offset, limit).getContent());
    }
}
