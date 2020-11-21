package ru.sd.app.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sd.web.dto.Book;

import java.util.List;

@Service
public class BookService {
    private final Logger logger = Logger.getLogger(BookService.class);
    private final ProjectRepository<Book> bookRepo;
    private boolean hasFilter;

    @Autowired
    public BookService(ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        logger.info("hasFilter - " + hasFilter);
        if (!hasFilter)
            return bookRepo.retreiveAll();
        else return ((BookRepository) bookRepo).getFilteredListBook();
    }

    public void saveBook(Book book) {
        bookRepo.store(book);
    }

    public boolean removeBook(Book bookToRemove) {
        return bookRepo.removeItem(bookToRemove);
    }

    public void setFilter(Book bookToFilter) {
        hasFilter = true;
        ((BookRepository) bookRepo).setFilterBook(bookToFilter);
    }

    public Book getFilter() {
        return ((BookRepository) bookRepo).getFilter();
    }

    public void clearFilter() {
        hasFilter = false;
        ((BookRepository) bookRepo).clearFilter();
    }
}
