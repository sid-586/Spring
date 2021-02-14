package ru.sd.MyBookShop.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Api(description = "genres")
@Controller
public class GenresController {
    private final Logger logger = Logger.getLogger(GenresController.class);

    @ApiOperation("getting genres")
    @GetMapping("/genres")
    public String getGenresPage() {
        logger.info("getGenresPage");
        return "genres/index";
    }

//    @ApiOperation("getting genres")
//    @GetMapping("/api/genres")
//    public String getGenres() {
//        logger.info("getGenresPage");
//        return "genres/index";
//    }
    
}
