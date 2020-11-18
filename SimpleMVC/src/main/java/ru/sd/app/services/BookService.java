package ru.sd.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sd.web.dto.Book;

import java.util.List;

@Service
public class BookService {
    private final ProjectRepository<Book> bookRepo;

    @Autowired
    public BookService(ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retreiveAll();
    }

    public void saveBook(Book book) {
        bookRepo.store(book);
    }

    public boolean removeBook(Book bookToRemove) {
        return bookRepo.removeItem(bookToRemove);
    }

    public void setFilter(Book bookToFilter) {
        ((BookRepository) bookRepo).setFilterBook(bookToFilter);
    }

    public List<Book> getBookFilter() {
        return ((BookRepository) bookRepo).getFilterBooks();
    }

    public void clearFilter() {
        ((BookRepository) bookRepo).clearFilter();
    }
}
