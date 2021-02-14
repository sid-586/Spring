package ru.sd.MyBookShop.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import ru.sd.MyBookShop.data.*;

import java.util.Date;
import java.util.GregorianCalendar;

@Configuration
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final Logger logger = Logger.getLogger(CommandLineRunnerImpl.class);
    ClientRepository clientRepository;
    BookRepository bookRepository;
    AuthorsRepository authorsRepository;

    @Autowired
    public CommandLineRunnerImpl(ClientRepository clientRepository, BookRepository bookRepository, AuthorsRepository authorsRepository) {
        this.clientRepository = clientRepository;
        this.bookRepository = bookRepository;
        this.authorsRepository = authorsRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        for (int i = 1; i <= 5; i++) {
            createClient(new Client(), i);
        }

        createAuthorByName("Twen", "Mark");
        createBook("Tom Sawyer"
                , "Mark Twain"
                , 600
                , 40
                , 0
                , new GregorianCalendar(2020, 01, 01).getTime()
                , "https://upload.wikimedia.org/wikipedia/commons/thumb/9/95/Tom_Sawyer_8c_1972_issue_U.S._stamp.jpg/220px-Tom_Sawyer_8c_1972_issue_U.S._stamp.jpg");
        createBook("The Titan"
                , "Theodore Dreiser"
                , 1500
                , 30
                , 1,
                new GregorianCalendar(2021, 01, 01).getTime()
                , "https://cv6.litres.ru/pub/c/elektronnaya-kniga/cover_415/51089767-theodore-dreiser-the-titan-51089767.jpg");

        Book readBook = readBookById(3);
        if (readBook != null) {
            logger.info("Reading book " + readBook.toString());
        } else logger.warn("No such book");

        Author readAuthor = readAuthorById(3);
        if (readAuthor != null) {
            logger.info("Reading author " + readAuthor.toString());
        } else logger.warn("No such author");

        Client readClient = readClientById(1);
        if (readClient != null) {
            logger.info("Reading client " + readClient.toString());
        } else logger.warn("No such client");

        Book updatedBook = updateBookById(3);
        if (updatedBook != null) {
            logger.info("This book was updated " + updatedBook.toString());
        } else logger.warn("No such book");

        Author updatedAurhor = updateAuthorById(3);
        if (updatedAurhor != null) {
            logger.info(" This author was updated " + updatedAurhor.toString());
        } else logger.warn("No such author");

        Client updatedClient = updateClientById(3);
        if (updatedClient != null) {
            logger.info("This Client was updared " + updatedClient.toString());
        } else logger.warn("No such Client");

        deleteBookById(4);
        deleteAuthorById(4);
        deleteClientById(2);
    }

    private void createBook(String title, String authorName, Integer priceOld,
                            Integer price, int isBestseller, Date pubDate,
                            String imageUrl) {
        Book book = new Book();
        book.setTitle(title);
        String lastName = authorName.split(" ", 2)[1];
        String firstName = authorName.split(" ", 2)[0];
        Author author = authorsRepository.findAuthorByLastNameAndFirstName(
                lastName, firstName);
        if (author == null) {
            logger.info("Creating new author when create new book");
            createAuthorByName(lastName, firstName);
            author = authorsRepository.findAuthorByLastNameAndFirstName(
                    lastName, firstName);
        }
        book.setAuthor(author);
        book.setPrice(price);
        book.setPriceOld(priceOld);
        book.setIsBestseller(isBestseller);
        book.setPublicationDate(pubDate);
        book.setImage(imageUrl);

        bookRepository.save(book);
    }

    private Book readBookById(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    private Book updateBookById(int id) {

        Book foundBook = bookRepository.findById(id).orElse(null);
        if (foundBook != null) {
            foundBook.setTitle("GARRY POTTER");
            bookRepository.save(foundBook);
        }
        return foundBook;
    }

    private void deleteBookById(int id) {
        bookRepository.findById(id).ifPresent(foundBook -> bookRepository.delete(foundBook));
    }

    private void createAuthorByName(String lastName, String firstName) {
        if (authorsRepository.findAuthorByLastNameAndFirstName(lastName,
                firstName) == null) {
            Author author = new Author();
            author.setLastName(lastName);
            author.setFirstName(firstName);
            authorsRepository.save(author);
        }
    }

    private Author readAuthorById(int id) {
        return authorsRepository.findById(id).orElse(null);
    }

    private Author updateAuthorById(int id) {
        Author foundAuthor = authorsRepository.findById(id).orElse(null);
        if (foundAuthor != null) {
            foundAuthor.setFirstName("Joann");
            foundAuthor.setLastName("Roaling");
            authorsRepository.save(foundAuthor);
        }
        return foundAuthor;
    }

    private void deleteAuthorById(int id) {
        authorsRepository.findById(id).ifPresent(foundBook -> authorsRepository.delete(foundBook));
    }

    private void createClient(Client client, int i) {

        client.setFirstName("Markus");
        client.setSurname("Rashford".concat(String.valueOf(i)));
        clientRepository.save(client);
    }

    private Client readClientById(int id) {
        return clientRepository.findById(id).orElse(null);
    }

    private Client updateClientById(int id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client != null) {
            client.setSurname("Trump");
            client.setFirstName("Donald");
            clientRepository.save(client);
        }
        return client;
    }

    private void deleteClientById(int id) {
        clientRepository.findById(id).ifPresent(foundBook -> clientRepository.delete(foundBook));
    }
}
