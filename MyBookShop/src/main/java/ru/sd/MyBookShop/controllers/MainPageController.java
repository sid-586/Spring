package ru.sd.MyBookShop.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/postponed.html")
    public String getFavoritesBooks() {
        return "postponed";
    }

    @RequestMapping("/cart.html")
    public String getCartPage() {
        logger.info("Getting cart page");
        return "cart";
    }

    @RequestMapping("/signin.html")
    public String getSignin() {
        return "signin.html";
    }

    @RequestMapping("/documents/index.html")
    public String getDocumentsPage() {
        return "documents/index.html";
    }

    @RequestMapping("/documents/slug.html")
    public String getDocumentSlug() {
        return "documents/slug.html";
    }

    @RequestMapping("/about.html")
    public String getAboutPage() {
        return "about.html";
    }

    @RequestMapping("/faq.html")
    public String getFaqPage() {
        return "faq.html";
    }

    @RequestMapping("/contacts.html")
    public String getContactsPage() {
        return "contacts";
    }

    @RequestMapping("/tags/index.html")
    public String getTags() {
        return "tags/index.html";
    }

}
