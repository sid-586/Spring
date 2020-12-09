package ru.sd.app.services;

import lombok.Getter;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.sd.web.dto.Book;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Getter
@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private ApplicationContext context;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> retreiveAll() {
        List<Book> bookList = jdbcTemplate.query("SELECT * FROM books_shelf",
                (ResultSet rs, int rowNum) -> {
                    Book book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setAuthor(rs.getString("author"));
                    book.setTitle(rs.getString("title"));
                    book.setSize(rs.getInt("size"));
                    return book;
                });
        return new ArrayList<>(bookList);
    }

    @Override
    public void store(Book book) {

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id",
                context.getBean(BookIdProvider.class).provideId(book));
        parameterSource.addValue("author", book.getAuthor());
        parameterSource.addValue("title", book.getTitle());
        parameterSource.addValue("size", book.getSize());
        jdbcTemplate.update("INSERT INTO books_shelf(id,author,title,size) " +
                "VALUES(:id, :author, :title, :size)", parameterSource);
        logger.info("store new book " + book.toString());

    }

    @Override
    public boolean removeItem(Book bookToRemove) {

        int countOfRemovedBooks = 0;
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", bookToRemove.getId());
        parameterSource.addValue("author", ".*" + bookToRemove.getAuthor() + ".*");
        parameterSource.addValue("title", ".*" + bookToRemove.getTitle() + ".*");
        parameterSource.addValue("size", bookToRemove.getSize());

        if (!bookToRemove.getAuthor().isEmpty()) {
            countOfRemovedBooks += jdbcTemplate
                    .update("DELETE FROM books_shelf WHERE author REGEXP " +
                            ":author", parameterSource);

        }
        if (!bookToRemove.getTitle().isEmpty()) {
            countOfRemovedBooks += jdbcTemplate
                    .update("DELETE FROM books_shelf WHERE title REGEXP :title", parameterSource);
        }
        if (bookToRemove.getId() != null) {
            countOfRemovedBooks += jdbcTemplate
                    .update("DELETE FROM books_shelf WHERE id = :id",
                            parameterSource);
        }
        if (bookToRemove.getSize() != null) {
            countOfRemovedBooks += jdbcTemplate
                    .update("DELETE FROM books_shelf WHERE size = :size",
                            parameterSource);
        }
        return countOfRemovedBooks != 0;
    }

    public List<Book> getFilteredListBook(Book filter) {

        logger.info("Filtration by book " + filter);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", ".*" + filter.getAuthor() + ".*");
        parameterSource.addValue("title", ".*" + filter.getTitle() + ".*");
        if (filter.getSize() != null) {
            parameterSource.addValue("size", filter.getSize());

            return jdbcTemplate.query("SELECT id,author," +
                            "title,size FROM books_shelf " +
                            "WHERE (author REGEXP :author) " +
                            "AND (title REGEXP :title) " +
                            "AND (size = :size)",
                    parameterSource,
                    (ResultSet rs, int numRow) -> {
                        Book book = new Book();
                        book.setId(rs.getInt("id"));
                        book.setAuthor(rs.getString("author"));
                        book.setTitle(rs.getString("title"));
                        book.setSize(rs.getInt("size"));
                        return book;
                    });
        } else {
            return jdbcTemplate.query("SELECT id,author," +
                            "title,size FROM books_shelf " +
                            "WHERE (author REGEXP :author) " +
                            "AND (title REGEXP :title)",
                    parameterSource,
                    (ResultSet rs, int numRow) -> {
                        Book book = new Book();
                        book.setId(rs.getInt("id"));
                        book.setAuthor(rs.getString("author"));
                        book.setTitle(rs.getString("title"));
                        book.setSize(rs.getInt("size"));
                        return book;
                    });
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws
            BeansException {
        this.context = applicationContext;
    }
}
