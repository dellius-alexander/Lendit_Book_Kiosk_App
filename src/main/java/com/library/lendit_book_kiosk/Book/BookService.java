package com.library.lendit_book_kiosk.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzTransactionManager;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Transactional
/**
 * Service layer control logic
 */
@Service(value = "BookService")
public class BookService implements Serializable {
    private final static Logger log = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Find books By Title
     * @param title book title
     * @return {@literal List<Book>}
     */
    public List<Book> getBooksByTitle(String title){

        List<Book> books = this.bookRepository.findBookByTitle(title);
        log.info("\nBooks: {}\n",books);
        return books;
    }
    /**
     * Find books By Title
     * @param title book title
     * @return {@literal List<Book>}
     */
    public List<BookSelection> getBooksBySelectionTitle(String title){

        List<BookSelection> books = this.bookRepository.findBookBySelectionTitle(title);
        log.info("\nBooks: {}\n",books);
        return books;
    }

    /**
     * Find book By Author
     * @param author
     * @return {@literal List<Book>}
     */
    public List<Book> getBooksByAuthor(String author){
        List<Book> books = this.bookRepository.findBookByAuthors(author);
        log.info("\nBooks: {}\n",books);
        return books;
    }


    /**
     * Find book By Author
     * @param author
     * @return {@literal List<Book>}
     */
    public List<BookSelection> getBooksBySelectionAuthor(String author){
        List<BookSelection> books = this.bookRepository.findBookBySelectionAuthors(author);
        log.info("\nBooks: {}\n",books);
        return books;
    }

    /**
     * Find book By Genres
     * @param genres
     * @return {@literal List<Book>}
     */
    public List<Book> getBooksByGenres(String genres){
        List<Book> books = this.bookRepository.findBookByGenres(genres);
        log.info("\nBooks: {}\n",books);
        return books;
    }

    /**
     * Find book By Genres
     * @param genres
     * @return {@literal List<Book>}
     */
    public List<BookSelection> getBooksBySelectionGenres(String genres){
        List<BookSelection> books = this.bookRepository.findBookBySelectionGenres(genres);
        log.info("\nBooks: {}\n",books);
        return books;
    }


    /**
     * Perform keyword search description
     * @param description
     * @return {@literal List<Book>}
     */
    public List<Book> getBooksByDescription(String description){
        List<Book> books = this.bookRepository.findBookByDescription(description);
        log.info("\nBooks: {}\n",books);
        return books;
    }

    /**
     * Perform keyword search description
     * @param description
     * @return {@literal List<Book>}
     */
    public List<BookSelection> getBooksBySelectionDescription(String description){
        List<BookSelection> books = this.bookRepository.findBookBySelectionDescription(description);
        log.info("\nBooks: {}\n",books);
        return books;
    }


    /**
     * Save all book entries
     * @param books
     * @return {@literal List<Book>}
     */
    @Async
    public void saveAll(Iterable<Book> books){
        List<Book> bks = this.bookRepository.saveAll(books);
        log.info("\nBooks: {}\n",bks);
    }
}
