package ru.sd.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sd.app.exceptions.BookShelfLoginException;
import ru.sd.web.dto.LoginForm;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
    private final Logger logger = Logger.getLogger(LoginController.class);

    @GetMapping
    public String login(Model model) {
        logger.info("GET / login return login_page");
        LoginForm loginForm = new LoginForm();
        model.addAttribute("loginForm", loginForm);
        logger.debug(loginForm.toString());

        return "login_page";
    }

    @PostMapping(value = "/auth")
    public String authentificate(@Valid LoginForm loginForm,
                                 BindingResult bindingResult, Model model) throws BookShelfLoginException {
        logger.info("bindingResult.hasErrors " + bindingResult.hasErrors() +
                " " + loginForm.toString());
        if (bindingResult.hasErrors()) {
            model.addAttribute("loginForm", loginForm);

            return "login_page";
        }
        throw new BookShelfLoginException("Invalid username and/or " +
                "password!");
    }

    @PostMapping(value = "/registration")
    public String signIn() {
        logger.debug("Redirect to registration");
        return "redirect:/login/registration";
    }
}
