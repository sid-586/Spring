package ru.sd.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sd.app.services.BookService;
import ru.sd.web.dto.Book;

@Controller
@RequestMapping(value = "/books")
public class BookShelfController {
    private final Logger logger = Logger.getLogger(BookShelfController.class);
    private final BookService bookService;
    private static final String BASE_PAGE = "redirect:/books/shelf";

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/shelf")
    public String books(Model model) {
        logger.info("got book");
        model.addAttribute("book", new Book());
        model.addAttribute("filterBook", bookService.getFilter());
        model.addAttribute("booklist", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(Book book) {
        bookService.saveBook(book);
        logger.info("Current repository size: " + bookService.getAllBooks().size());
        return BASE_PAGE;
    }

    @PostMapping("/remove")
    public String removeBook(Book bookToRemove) {
        if (bookService.removeBook(bookToRemove)) {
            logger.info("Current repository size: " + bookService.getAllBooks().size());
        } else logger.info("There is not such book");
        return BASE_PAGE;
    }

    @PostMapping("/filter")
    public String setFilterBook(Book bookToFilter) {
        bookService.setFilter(bookToFilter);
        logger.info("Filtered list size: " + bookService.getAllBooks().size());

        return BASE_PAGE;
    }

    @PostMapping("/clear")
    public String clearFilter() {
        bookService.clearFilter();
        logger.info("Clear filter. Filtered list size: " + bookService.getAllBooks().size());
        return BASE_PAGE;
    }

    @PostMapping("/logout")
    public String logout() {
        logger.info("Logout");
        bookService.clearFilter();
        return "redirect:/login";
    }
}
