package ru.sd.app.services;

import lombok.Getter;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;
import ru.sd.web.dto.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();
    private final Book filter = new Book();
    private ApplicationContext context;

    @Override
    public List<Book> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        if (checkEmptyFields(book)) {
            book.setId(context.getBean(BookIdProvider.class).provideId(book));
            logger.info("store new book " + book.toString());
            repo.add(book);
        } else logger.info("All fields are empty");
    }

    @Override
    public boolean removeItem(Book bookToRemove) {
        List<Book> booksToRemove = new ArrayList<>();
        if (checkEmptyFields(bookToRemove) || bookToRemove.getId() != null) {

            logger.info("checkEmptyFields" + bookToRemove.toString());
            for (Book book : retreiveAll()) {

                if (checkFillAndEqualFields(book)) {
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

    private boolean checkFillAndEqualFields(Book book) {
        return (filter.getAuthor().isEmpty() || filter.getAuthor().equalsIgnoreCase(book.getAuthor()))
                && (filter.getTitle().isEmpty() || filter.getTitle().equalsIgnoreCase(book.getTitle()))
                && (filter.getSize() == null || filter.getSize().equals(book.getSize()));
    }

    public void setFilterBook(Book bookToFilter) {
        logger.info("setting filterBook " + bookToFilter);
        if (filter.getAuthor() == null || filter.getAuthor().trim().isEmpty()) {
            filter.setAuthor(bookToFilter.getAuthor());
        }
        if (filter.getTitle() == null || filter.getTitle().trim().isEmpty()) {
            logger.info("getTitle() == null");
            filter.setTitle(bookToFilter.getTitle());
        }
        if (filter.getSize() == null) {
            filter.setSize(bookToFilter.getSize());
        }
        logger.info("Filter book - " + filter.toString());
    }

    public List<Book> getFilteredListBook() {

        if (filter.getAuthor() == null && filter.getTitle() == null && filter.getSize() == null) {
            logger.info("Repo size " + repo.size());
            return retreiveAll();
        } else {
            logger.info("Filtration by book " + filter + " repo size " + repo.size());
            return retreiveAll()
                    .stream()
                    .filter(this::checkFillAndEqualFields)
                    .collect(Collectors.toList());
        }
    }

    public void clearFilter() {
        logger.info("Clear all filters");
        filter.setAuthor(null);
        filter.setTitle(null);
        filter.setSize(null);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws
            BeansException {
        this.context = applicationContext;
    }
}
