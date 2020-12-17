package ru.sd.MyBookShop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.expression.Strings;
import ru.sd.MyBookShop.services.BookService;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/authors")
public class AuthorsController {
    private BookService bookService;

    @Autowired
    public AuthorsController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String getAuthorsPage(Model model) {

        List<String> alphabet = IntStream
                .rangeClosed('A', 'Z')
                .mapToObj(c -> "" + (char) c)
                .collect(Collectors.toList());
        model.addAttribute("letters", alphabet);
        model.addAttribute("authors", bookService.getAuthors());
        Logger.getLogger(AuthorsController.class.getName()).info("Get authors" +
                " page + list of authors");
        return "authors/index";
    }

}
