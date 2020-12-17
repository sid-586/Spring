package ru.sd.MyBookShop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sd.MyBookShop.services.BookService;

import java.util.logging.Logger;

@Controller
public class MainPageController {
    private BookService bookService;

    @Autowired
    public MainPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/bookshop/main")
    public String getMainPage(Model model) {
        model.addAttribute("bookData", bookService.getBookList());
        model.addAttribute("recentBooks", bookService.getRecentBookList());
        model.addAttribute("popularBooks", bookService.getPopularBookList());
        Logger.getLogger(MainPageController.class.getName()).info("Get MainPage");
        return "index";
    }

}
