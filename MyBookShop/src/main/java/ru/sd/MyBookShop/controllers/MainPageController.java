package ru.sd.MyBookShop.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sd.MyBookShop.data.Book;
import ru.sd.MyBookShop.data.BookPage;
import ru.sd.MyBookShop.data.SearchWordDto;
import ru.sd.MyBookShop.services.BookService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainPageController {
    private final Logger logger = Logger.getLogger(MainPageController.class);
    private BookService bookService;

    public MainPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute("searchResult")
    public List<Book> searchResult() {
        return new ArrayList<>();
    }

    @GetMapping("/bookshop/main")
    public String getMainPage(Model model) {
        model.addAttribute("searchPlaceholder", "new search");
        model.addAttribute("bookData",
                bookService.getPageOfRecommendedBooks(0, 20).getContent());
        model.addAttribute("recentBooks",
                bookService.getPageOfRecentBooks(null, null, 0, 20)
                        .getContent());
        model.addAttribute("popularBooks",
                bookService.getPageOfPopularBooks(0, 20));
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

    @GetMapping(value = {"/search", "/search/{searchWord}"})
    public String getSearchResult(@PathVariable(value = "searchWord", required = false)
                                          SearchWordDto searchWordDto, Model model) {
        model.addAttribute("searchWordDto", searchWordDto);
        model.addAttribute("searchResult",
                bookService
                        .getPageOfSearchResultBooks(searchWordDto.getExample(), 0, 5)
                        .getContent());
        model.addAttribute("searchFullResult",
                bookService.getBooksByTitle(searchWordDto.getExample()));
        return "/search/index";
    }

    @GetMapping("/search/page/{searchWord}")
    @ResponseBody
    public BookPage getNextPageOfSearchResult(@RequestParam("offset") Integer offset,
                                              @RequestParam("limit") Integer limit,
                                              @PathVariable(value =
                                                      "searchWord",
                                                      required = false) SearchWordDto searchWordDto) {
        return new BookPage(bookService.getPageOfSearchResultBooks(searchWordDto.getExample()
                , offset, limit).getContent());
    }

}
