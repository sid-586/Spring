package ru.sd.MyBookShop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
public class AuthorPersonalDataController {

    @RequestMapping("/authors/slug.html")
    public String getAuthorPersonalData() {
        Logger.getLogger(AuthorPersonalDataController.class.getName()).info(
                "Author's personal data");
        return "authors/slug";
    }
}
