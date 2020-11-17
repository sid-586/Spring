package ru.sd.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sd.app.services.BookService;
import ru.sd.web.dto.Book;

@Controller
@RequestMapping(value = "/books")
public class BookShelfController {
    private Logger logger = Logger.getLogger(BookShelfController.class);
    private BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping(value = "/shelf")
    public String books(Model model) {
        logger.info("got book");
        model.addAttribute("book", new Book());
        model.addAttribute("booklist", bookService.getAllBooks());
        model.addAttribute("filter", new Book());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(Book book) {
        bookService.saveBook(book);
        logger.info("Current repository size: " + bookService.getAllBooks().size());
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove")
    public String removeBook(Book bookToRemove) {
        if(bookService.removeBookById(bookToRemove)) {
            logger.info("Current repository size: " + bookService.getAllBooks().size());
        } else logger.info("There is not such book");
        return "redirect:/books/shelf";
    }

    @PostMapping("/logout")
    public String logout() {
        logger.info("Logout");
        return "redirect:/login";
    }
}
