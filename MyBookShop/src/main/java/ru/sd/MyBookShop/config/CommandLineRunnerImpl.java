package ru.sd.MyBookShop.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import ru.sd.MyBookShop.data.*;

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
        createBook("Tom Soyer", "Mark Twen", "600.00 руб.", "450.00 руб.", false);
        createBook("The Titan", "Theodore Dreiser", "1500.00 руб.", "1000.00 " +
                "руб.", true);

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

    private void createBook(String title, String authorName, String priceOld,
                            String price, boolean isBestseller) {
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
        book.setBestseller(isBestseller);

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
        Author author = new Author();
        author.setLastName(lastName);
        author.setFirstName(firstName);
        authorsRepository.save(author);
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
