package ru.sd.MyBookShop.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthorPersonalDataController {
    private final Logger logger =
            Logger.getLogger(AuthorPersonalDataController.class);

    @RequestMapping("/authors/slug.html")
    public String getAuthorPersonalData() {
        logger.info("Author's personal data");
        return "authors/slug";
    }
}
