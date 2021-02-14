package ru.sd.MyBookShop.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorsRepository extends JpaRepository<Author, Integer> {

    Author findAuthorByLastNameAndFirstName(String lastName, String firstName);

}
