package ru.sd.MyBookShop.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sd.MyBookShop.data.BookPage;
import ru.sd.MyBookShop.data.DateFromDto;
import ru.sd.MyBookShop.data.DateToDto;
import ru.sd.MyBookShop.services.BookService;

import java.text.ParseException;
import java.util.Date;

@Controller
@RequestMapping("/books")
public class RecentController {
    private final Logger logger =
            Logger.getLogger(RecentController.class);

    private BookService bookService;

    public RecentController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = {"/recent", "/recent/{from}&{to}"})
    public String getRecentBooks(Model model,
                                 @PathVariable(value = "from", required =
                                         false) DateFromDto dateFromDto,
                                 @PathVariable(value = "to",
                                         required = false) DateToDto dateToDto) {

        logger.info("getRecentBooks. from = " + dateFromDto + " to " + dateToDto);
        /*
        Мне представляется, что в модель нужно передавать не новый объект
        DTO, а то, что мы получаем в запросе - dateFromDto и dateToDto,
        однако в этом случае возникает ошибка java.lang.IllegalStateException: Neither BindingResult nor plain target object for bean name 'dateFromDto' available as request attribute
         */
        model.addAttribute("dateFromDto", new DateFromDto());
        model.addAttribute("dateToDto", new DateToDto());
        /*
        Здесь поставил null временно, чтобы избежать NPE
         */
        model.addAttribute("recentBooks",
                bookService.getPageOfRecentBooks(null, null, 0, 20).getContent());
        return "books/recent";
    }

    @GetMapping(value = {"/recent/next_page", "/recent/next_page/{from}&{to}"})
    @ResponseBody
    public BookPage getNextRecentBookPage
            (@RequestParam(name = "offset") Integer offset,
             @RequestParam(name = "limit") Integer limit,
             @PathVariable(value = "from", required = false) DateFromDto from,
             @PathVariable(value = "to", required = false) DateToDto to) {

        logger.info("Get next page of recent books from " + from + " to " + to);
        return new BookPage(bookService.getPageOfRecentBooks(null, null
                ,
                offset, limit).getContent());
    }
}
