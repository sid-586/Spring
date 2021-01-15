package ru.sd.MyBookShop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.sd.MyBookShop.data.Author;
import ru.sd.MyBookShop.data.Book;
import ru.sd.MyBookShop.data.BookRepository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBookList() {
        return bookRepository.findAll();
    }

    //todo
    //после определения критериев выборки
    public List<Book> getRecentBookList() {
        return bookRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    //todo
    //после определения критериев выборки
    public List<Book> getPopularBookList() {
        return bookRepository.findBooksByTitleContaining("so");
    }

}
