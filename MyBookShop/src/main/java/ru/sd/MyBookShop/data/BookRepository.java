package ru.sd.MyBookShop.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findBooksByAuthorIdIsNullAndAuthorIsNull();

    List<Book> findBooksByTitleContaining(String keyword);

    List<Book> findBooksByAuthorLastName(String lastName);

    @Query("from Book")
    List<Book> customFindAllBooks();

    Page<Book> findBooksByPublicationDateIsBetween(Date from, Date to,
                                                   Pageable nextPage);

    List<Book> findBooksByAuthorFirstNameContaining(String firstName);

    List<Book> findBooksByPriceOldBetween(Integer min, Integer max);

    List<Book> findBooksByPriceOldIs(Integer price);

    @Query("from Book where isBestseller=1")
    List<Book> getBestsellers();

    @Query(value = "SELECT * FROM books WHERE discount=(SELECT MAX(discount) " +
            "FROM books)", nativeQuery = true)
    List<Book> getBooksWithMaxPrice();

    Page<Book> findBooksByTitleContaining(String searchWord,
                                          Pageable nextPage);

}




