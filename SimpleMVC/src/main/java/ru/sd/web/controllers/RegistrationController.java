package ru.sd.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sd.app.exceptions.BookShelfLoginException;
import ru.sd.app.services.LoginService;
import ru.sd.web.dto.Account;

import javax.validation.Valid;

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
        logger.info("got regform");

        return "registration_form";
    }

    @PostMapping(value = "/sign_in")
    public String registrate(@Valid Account account,
                             BindingResult bindingResult, Model model) throws BookShelfLoginException {
        logger.info("input into registration");
        if (bindingResult.hasErrors()) {
            model.addAttribute("account", account);

            return "registration_form";
        }
        loginService.registrate(account);
        logger.info("New account was created " + account.toString());

        return "redirect:/books/shelf";
    }
}
