package ru.sd.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sd.app.services.LoginService;
import ru.sd.web.dto.Account;

@Controller
@RequestMapping(value = "/login/registration")
public class RegistrationController {
    private final Logger logger =
            Logger.getLogger(RegistrationController.class);
    private final LoginService loginService;

    @Autowired
    private RegistrationController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String registration(Model model) {
        logger.info("Input registration form");
        Account account = new Account();
        model.addAttribute("account", account);
        logger.debug("got regform");
        return "registration_form";
    }

    @PostMapping(value = "/sign_in")
    public String registrate(Account account) {
        logger.debug("input into registration");
        if (loginService.registrate(account)) {
            logger.info("New account was created " + account.toString());
            return "redirect:/books/shelf";
        } else {
            logger.info("Uncorrect input");
            return "redirect:/login";
        }
    }
}
