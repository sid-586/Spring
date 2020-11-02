package ru.sd.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    private final Logger logger = Logger.getLogger(LoginController.class);

    @GetMapping(value = "/login")
    public ModelAndView login() {
        logger.info("GET / login return login_page");
        return new ModelAndView("login_page");
    }

}
