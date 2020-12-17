package ru.sd.MyBookShop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.sd.MyBookShop.dto.Author;
import ru.sd.MyBookShop.dto.Book;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getBookList() {
        List<Book> bookList = jdbcTemplate
                .query("SELECT * FROM books",
                        (ResultSet rs, int intRow) -> {
                            Book book = new Book();
                            book.setId(rs.getInt("id"));
                            book.setAuthor(rs.getString("author"));
                            book.setTitle(rs.getString("title"));
                            book.setPriceOld(rs.getString("priceOld"));
                            book.setPrice(rs.getString("price"));
                            return book;
                        });
        return new ArrayList<>(bookList);
    }

    //todo
    //после опредления критериев выборки
    public List<Book> getRecentBookList() {
        List<Book> bookList = jdbcTemplate
                .query("SELECT * FROM books",
                        (ResultSet rs, int intRow) -> {
                            Book book = new Book();
                            book.setId(rs.getInt("id"));
                            book.setAuthor(rs.getString("author"));
                            book.setTitle(rs.getString("title"));
                            book.setPriceOld(rs.getString("priceOld"));
                            book.setPrice(rs.getString("price"));
                            return book;
                        });
        return new ArrayList<>(bookList);
    }

    //todo
    //после опредления критериев выборки
    public List<Book> getPopularBookList() {
        List<Book> bookList = jdbcTemplate
                .query("SELECT * FROM books",
                        (ResultSet rs, int intRow) -> {
                            Book book = new Book();
                            book.setId(rs.getInt("id"));
                            book.setAuthor(rs.getString("author"));
                            book.setTitle(rs.getString("title"));
                            book.setPriceOld(rs.getString("priceOld"));
                            book.setPrice(rs.getString("price"));
                            return book;
                        });
        return new ArrayList<>(bookList);
    }

    public List<Author> getAuthors() {

        return jdbcTemplate
                .query("SELECT * FROM authors",
                        (ResultSet rs, int intRow) -> {
                            Author author = new Author();
                            author.setId(rs.getInt("id"));
                            author.setName(rs.getString("author"));
                            return author;
                        });
    }
}
