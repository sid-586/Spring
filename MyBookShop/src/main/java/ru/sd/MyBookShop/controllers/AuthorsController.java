package ru.sd.MyBookShop.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sd.MyBookShop.services.AuthorsService;
import ru.sd.MyBookShop.services.BookService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/authors")
public class AuthorsController {
    private final Logger logger = Logger.getLogger(AuthorsController.class);
    private AuthorsService authorsService;

    @Autowired
    public AuthorsController(AuthorsService authorsService) {
        this.authorsService = authorsService;
    }

    @GetMapping
    public String getAuthorsPage(Model model) {

        model.addAttribute("letters", authorsService.getAlphabet());
        model.addAttribute("authorsMap", authorsService.getAuthorsMap());
        logger.info("Get authors page + list of authors");
        return "authors/index";
    }
}
