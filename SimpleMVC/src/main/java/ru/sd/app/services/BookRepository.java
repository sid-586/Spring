package ru.sd.app.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.sd.web.dto.Book;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository implements ProjectRepository<Book> {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();

    @Override
    public List<Book> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        if (checkEmptyFields(book)) {
            book.setId(book.hashCode());
            logger.info("store new book " + book.toString());
            repo.add(book);
        } else logger.info("All fields are empty");
    }

    @Override
    public boolean removeItemById(Book bookToRemove) {
        List<Book> booksToRemove = new ArrayList<>();
        if (checkEmptyFields(bookToRemove) || bookToRemove.getId() != null) {

            logger.info("checkEmptyFields" + bookToRemove.toString());
            for (Book book : retreiveAll()) {

                if ((bookToRemove.getId() == null || bookToRemove.getId().equals(book.getId()))
                        && (bookToRemove.getAuthor().isEmpty() || bookToRemove.getAuthor().equals(book.getAuthor()))
                        && (bookToRemove.getTitle().isEmpty() || bookToRemove.getTitle().equals(book.getTitle()))
                        && (bookToRemove.getSize() == null || bookToRemove.getSize().equals(book.getSize()))) {
                    logger.info("remove book: " + book);
                    booksToRemove.add(book);
                }
            }
        }
        return repo.removeAll(booksToRemove);
    }

    private boolean checkEmptyFields(Book book) {
        return !book.getAuthor().isEmpty() ||
                !book.getTitle().isEmpty() ||
                book.getSize() != null;
    }
}
