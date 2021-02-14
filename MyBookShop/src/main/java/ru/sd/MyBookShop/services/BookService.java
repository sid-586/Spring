package ru.sd.MyBookShop.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.sd.MyBookShop.controllers.BooksController;
import ru.sd.MyBookShop.data.Book;
import ru.sd.MyBookShop.data.BookRepository;

import java.util.Date;
import java.util.List;

@Service
public class BookService {
    private final Logger logger = Logger.getLogger(BooksController.class);
    BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Page<Book> getPageOfRecommendedBooks(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findAll(nextPage);
    }

    public Page<Book> getPageOfRecentBooks(Date from, Date to, Integer offset,
                                           Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit, Sort.Direction.DESC
                , "publicationDate");
        if (from == null && to == null) {
            logger.info("Get recent books without dates");
            return bookRepository.findAll(nextPage);
        } else return bookRepository.findBooksByPublicationDateIsBetween(from,
                to, nextPage);
    }

    public Page<Book> getPageOfSearchResultBooks(String searchWord,
                                                 Integer offset,
                                                 Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBooksByTitleContaining(searchWord, nextPage);
    }

    //todo
    //после реализации алгоритма определения популярности
    public Page<Book> getPageOfPopularBooks(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit, Sort.Direction.DESC
                , "isBestseller");
        return bookRepository.findAll(nextPage);
    }

    public List<Book> getBooksByAuthor(String authorSurname) {
        return bookRepository.findBooksByAuthorLastName(authorSurname);
    }

    public List<Book> getBooksByAuthorsFirstName(String firstName) {
        return bookRepository.findBooksByAuthorFirstNameContaining(firstName);
    }

    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findBooksByTitleContaining(title);
    }

    public List<Book> getBooksWithPriceBetween(Integer minPrice,
                                               Integer maxPrice) {
        return bookRepository.findBooksByPriceOldBetween(minPrice, maxPrice);
    }

    public List<Book> getBooksWithPrice(Integer priceValue) {
        return bookRepository.findBooksByPriceOldIs(priceValue);
    }

    public List<Book> getBooksWithMaxDiscount() {
        return bookRepository.getBooksWithMaxPrice();
    }

    public List<Book> getBestsellers() {
        return bookRepository.getBestsellers();
    }

    //todo
    //после реализации алгоритма определения популярности
    public List<Book> getPopularBookList() {
        return bookRepository.findBooksByTitleContaining("so");
    }

}
