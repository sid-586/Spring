package ru.sd.app.services;

import lombok.Getter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sd.web.dto.Book;
import ru.sd.web.dto.BookToFilter;
import ru.sd.web.dto.BookToRemove;

import java.util.List;

@Service
public class BookService {
    private final Logger logger = Logger.getLogger(BookService.class);
    private final ProjectRepository<Book> bookRepo;
    @Getter
    private final Book filter = new Book();
    private boolean hasFilter;

    @Autowired
    public BookService(ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {

        if (!hasFilter)
            return bookRepo.retreiveAll();
        else return ((BookRepository) bookRepo).getFilteredListBook(filter);
    }

    public void saveBook(Book book) {
        bookRepo.store(book);
    }

    public boolean removeBook(BookToRemove bookToRemove) {
        Book book = new Book(bookToRemove.getId(),
                bookToRemove.getAuthor(),
                bookToRemove.getTitle(),
                bookToRemove.getSize());
        return bookRepo.removeItem(book);
    }

    public void setFilter(BookToFilter bookToFilter) {
        hasFilter = true;

        logger.info("setting filterBook " + bookToFilter);
        if (filter.getAuthor() == null || filter.getAuthor().trim().isEmpty()) {
            filter.setAuthor(bookToFilter.getAuthor());
        }
        if (filter.getTitle() == null || filter.getTitle().trim().isEmpty()) {
            filter.setTitle(bookToFilter.getTitle());

        }
        if (filter.getSize() == null) {
            filter.setSize(bookToFilter.getSize());
        }

        logger.info("Filter book - " + filter.toString());
    }

    public void clearFilter() {
        hasFilter = false;

        logger.info("Clear all filters");
        filter.setAuthor(null);
        filter.setTitle(null);
        filter.setSize(null);
    }
}
