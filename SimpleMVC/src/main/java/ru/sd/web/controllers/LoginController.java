package ru.sd.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sd.app.services.BookService;
import ru.sd.app.services.LoginService;
import ru.sd.web.dto.LoginForm;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
    private final Logger logger = Logger.getLogger(LoginController.class);
    private final LoginService loginService;

    @Autowired
    private LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String login(Model model) {
        logger.info("GET / login return login_page");
        LoginForm loginForm = new LoginForm();
        model.addAttribute("loginForm", loginForm);
        logger.debug(loginForm.toString());
        return "login_page";
    }

    @PostMapping(value = "/auth")
    public String authentificate(LoginForm loginForm) {
        if (loginService.authentificate(loginForm)) {
            logger.info("Authorization was successful " + loginForm.toString());

            return "redirect:/books/shelf";
        } else {
            logger.info("Authorization was failed " + loginForm.toString());
            return "redirect:/login";
        }
    }

    @PostMapping(value = "/registration")
    public String signIn() {
        logger.debug("Redirect to registration");
        return "redirect:/login/registration";
    }
}
