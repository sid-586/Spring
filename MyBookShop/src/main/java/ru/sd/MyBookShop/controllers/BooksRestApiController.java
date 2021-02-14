package ru.sd.MyBookShop.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sd.MyBookShop.data.Book;
import ru.sd.MyBookShop.services.BookService;

import java.util.List;

@Controller
@Api(description = "book data api")
@RequestMapping("/api/books")
public class BooksRestApiController {
    private final Logger logger = Logger.getLogger(BooksRestApiController.class);

    private BookService bookService;

    public BooksRestApiController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/by-author")
    @ApiOperation("method to get booklist by author's surname")
    public ResponseEntity<List<Book>> getBooksByAuthor
            (@RequestParam(name = "author") String authorSurname) {
        return ResponseEntity.ok(bookService.getBooksByAuthor(authorSurname));
    }

    @GetMapping("/by-name-of-author")
    @ApiOperation("method to get booklist by author's first name")
    public ResponseEntity<List<Book>> getBooksByAuthorName
            (@RequestParam(name = "author") String authorName) {
        return ResponseEntity.ok(bookService.getBooksByAuthorsFirstName(authorName));
    }

    @GetMapping("/by-title")
    @ApiOperation("operation to get booklist by title")
    public ResponseEntity<List<Book>> getBooksByTitle
            (@RequestParam(name = "title") String title) {
        return ResponseEntity.ok(bookService.getBooksByTitle(title));
    }

    @GetMapping("/by-price-range")
    @ApiOperation("method to get list of books by price range between min " +
            "price and max price")
    public ResponseEntity<List<Book>> getBooksWithPriceRange
            (@RequestParam(name = "min") Integer minPrice,
             @RequestParam(name = "max") Integer maxPrice) {
        return ResponseEntity.ok(bookService.getBooksWithPriceBetween(minPrice, maxPrice));
    }

    @GetMapping("/by-price")
    @ApiOperation("get books with exact price ")
    public ResponseEntity<List<Book>> getBooksWithPrice
            (@RequestParam(name = "price") Integer price) {
        return ResponseEntity.ok(bookService.getBooksWithPrice(price));
    }

    @GetMapping("/by-max-discount")
    @ApiOperation("get books with max discount")
    public ResponseEntity<List<Book>> getBooksWithMaxDiscount() {
        return ResponseEntity.ok(bookService.getBooksWithMaxDiscount());
    }

    @GetMapping("/bestsellers")
    @ApiOperation("get bestseller books (which is_bestseller=1)")
    public ResponseEntity<List<Book>> getBestsellers() {
        return ResponseEntity.ok(bookService.getBestsellers());
    }

//    @GetMapping("/recent")
//    public String getRecentBooks() {
//        logger.info("getRecentBooks");
//        return "books/recent";
//    }
//
//    @GetMapping("/popular")
//    public String getPopularBooks() {
//        logger.info("getPopularBooks");
//        return "books/popular";
//    }

}

