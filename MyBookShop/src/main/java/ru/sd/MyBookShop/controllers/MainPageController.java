package ru.sd.MyBookShop.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sd.MyBookShop.services.BookService;

@Controller
public class MainPageController {
    private final Logger logger = Logger.getLogger(MainPageController.class);
    private BookService bookService;

    public MainPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/bookshop/main")
    public String getMainPage(Model model) {
        model.addAttribute("searchPlaceholder", "new search");
        model.addAttribute("bookData", bookService.getBookList());
        model.addAttribute("recentBooks", bookService.getRecentBookList());
        model.addAttribute("popularBooks", bookService.getPopularBookList());
        logger.info("Get MainPage");
        return "index";
    }
}
