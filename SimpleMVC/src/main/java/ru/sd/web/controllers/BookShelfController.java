package ru.sd.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sd.app.services.BookService;
import ru.sd.web.dto.Book;
import ru.sd.web.dto.BookToFilter;
import ru.sd.web.dto.BookToRemove;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/books")
public class BookShelfController {
    private final Logger logger = Logger.getLogger(BookShelfController.class);
    private final BookService bookService;
    private static final String BASE_PAGE = "redirect:/books/shelf";
    private static final String BASIC_VIEW = "book_shelf";

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/shelf")
    public String books(Model model) {
        logger.info("got book");
        model.addAttribute("book", new Book());
        model.addAttribute("bookToFilter", new BookToFilter());
        model.addAttribute("filterBook", bookService.getFilter());
        model.addAttribute("bookToRemove", new BookToRemove());
        model.addAttribute("booklist", bookService.getAllBooks());

        return BASIC_VIEW;
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("bookToFilter", new BookToFilter());
            model.addAttribute("filterBook", bookService.getFilter());
            model.addAttribute("bookToRemove", new BookToRemove());
            model.addAttribute("booklist", bookService.getAllBooks());

            return BASIC_VIEW;
        } else {
            bookService.saveBook(book);
            logger.info("Current repository size: " + bookService.getAllBooks().size());
            return BASE_PAGE;
        }
    }

    @PostMapping("/remove")
    public String removeBook(@Valid BookToRemove bookToRemove,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            logger.warn("bindingResult.hasErrors");
            model.addAttribute("book", new Book());
            model.addAttribute("bookToFilter", new BookToFilter());
            model.addAttribute("filterBook", bookService.getFilter());
            model.addAttribute("bookToRemove", bookToRemove);
            model.addAttribute("booklist", bookService.getAllBooks());

            return BASIC_VIEW;
        } else {
            if (bookService.removeBook(bookToRemove)) {
                logger.info("Current repository size: " + bookService.getAllBooks().size());
            } else logger.info("There is not such book");
            return BASE_PAGE;
        }
    }

    @PostMapping("/filter")
    public String setFilterBook(@Valid BookToFilter bookToFilter,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            logger.warn("bindingResult.hasErrors");
            model.addAttribute("book", new Book());
            model.addAttribute("bookToFilter", bookToFilter);
            model.addAttribute("filterBook", bookService.getFilter());
            model.addAttribute("bookToRemove", new BookToRemove());
            model.addAttribute("booklist", bookService.getAllBooks());

            return BASIC_VIEW;
        } else {
            bookService.setFilter(bookToFilter);
            logger.info("Filtered list size: " + bookService.getAllBooks().size());

            return BASE_PAGE;
        }
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
