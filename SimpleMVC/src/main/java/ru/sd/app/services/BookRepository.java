package ru.sd.app.services;

import lombok.Getter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.sd.web.dto.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Repository
public class BookRepository implements ProjectRepository<Book> {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();
    private final List<Book> filterBooks = new ArrayList<>();
    private List<Book> filteredBookList = new ArrayList<>();

    @Override
    public List<Book> retreiveAll() {

        if (filterBooks.isEmpty()) {
            logger.debug("Repo size " + repo.size());
            return new ArrayList<>(repo);
        } else {
            if (filteredBookList.isEmpty()) {

                for (Book filterBook : filterBooks) {
                    logger.info("Filtration by book " + filterBook + " repo size " + repo.size());
                    filteredBookList.addAll(repo
                            .stream()
                            .filter(f -> checkFillAndEqualFields(f, filterBook))
                            .distinct()
                            .collect(Collectors.toList()));
                }
            } else {
                for (Book filterBook : filterBooks) {
                    logger.info("Filtration by book " + filterBook + " repo size " + repo.size());
                    filteredBookList = filteredBookList
                            .stream()
                            .filter(f -> checkFillAndEqualFields(f, filterBook))
                            .distinct()
                            .collect(Collectors.toList());
                }
            }
            return filteredBookList;
        }
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
    public boolean removeItem(Book bookToRemove) {
        List<Book> booksToRemove = new ArrayList<>();
        if (checkEmptyFields(bookToRemove) || bookToRemove.getId() != null) {

            logger.info("checkEmptyFields" + bookToRemove.toString());
            for (Book book : retreiveAll()) {

                if (checkFillAndEqualFields(book, bookToRemove)) {
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

    private boolean checkFillAndEqualFields(Book bookFromRepo,
                                            Book bookFromUser) {
        return (bookFromUser.getId() == null || bookFromUser.getId().equals(bookFromRepo.getId()))
                && (bookFromUser.getAuthor().isEmpty() || bookFromUser.getAuthor().equalsIgnoreCase(bookFromRepo.getAuthor()))
                && (bookFromUser.getTitle().isEmpty() || bookFromUser.getTitle().equalsIgnoreCase(bookFromRepo.getTitle()))
                && (bookFromUser.getSize() == null || bookFromUser.getSize().equals(bookFromRepo.getSize()));
    }

    public void setFilterBook(Book bookToFilter) {
        if (checkEmptyFields(bookToFilter)) {
            logger.info("setting filterBook " + bookToFilter);
            filterBooks.add(bookToFilter);
        }
    }

    public void clearFilter() {
        logger.info("Clear all filters");
        filterBooks.clear();
    }
}
